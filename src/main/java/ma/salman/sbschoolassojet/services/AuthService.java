package ma.salman.sbschoolassojet.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.auth.LoginRequest;
import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.models.Utilisateur;
import ma.salman.sbschoolassojet.repositories.UtilisateurRepository;
import ma.salman.sbschoolassojet.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurRepository utilisateurRepository;

    public Map<String, Object> authenticateUser(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Map<String, Object> response = new HashMap<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication.isAuthenticated()) {
                Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

                // EXTRAIRE LES RÔLES DEPUIS L'ENTITÉ UTILISATEUR (pas depuis userPrincipal)
                List<String> roles = List.of("ROLE_" + utilisateur.getRole().name());

                // Générer les tokens
                String accessToken = generateAccessToken(utilisateur, roles);
                String refreshToken = generateRefreshToken(utilisateur);

                // Construire la réponse
                response.put("token", accessToken);
                response.put("refreshToken", refreshToken);

            }
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Nom d'utilisateur ou mot de passe incorrect");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'authentification: " + e.getMessage());
        }

        return response;
    }
    public String verifyToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Utilisateur utilisateur = utilisateurRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userDetails.getId()));

        return utilisateur.getRole().name();
    }

    public boolean checkUsernameExists(String username) {
        return utilisateurRepository.existsByUsername(username);
    }

    public boolean checkEmailExists(String email) {
        return utilisateurRepository.existsByEmail(email);
    }

    public Role[] getRoles() {
        return Role.values();
    }

    private String generateAccessToken(Utilisateur utilisateur, List<String> roles) {
        Algorithm algorithm = Algorithm.HMAC256("secretKey");
        Date now = new Date();

        return JWT.create()
                .withSubject(utilisateur.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(new Date(System.currentTimeMillis() + 900000)) // 15 min
                .withIssuer("https://salman-khan")
                .withClaim("id", utilisateur.getId())
                .withClaim("email", utilisateur.getEmail())
                .withClaim("nom", utilisateur.getNom())
                .withClaim("prenom", utilisateur.getPrenom())
                .withClaim("roles", roles)
                .withClaim("type", "access")
                .sign(algorithm);
    }

    private String generateRefreshToken(Utilisateur utilisateur) {
        Algorithm algorithm = Algorithm.HMAC256("secretKey");
        Date now = new Date();

        return JWT.create()
                .withSubject(utilisateur.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(new Date(System.currentTimeMillis() + 604800000L)) // 7 jours
                .withIssuer("https://salman-khan")
                .withClaim("id", utilisateur.getId())
                .withClaim("type", "refresh")
                .sign(algorithm);
    }
}