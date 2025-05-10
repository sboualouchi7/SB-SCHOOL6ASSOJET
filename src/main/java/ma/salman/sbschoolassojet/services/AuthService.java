package ma.salman.sbschoolassojet.services;

import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.auth.JwtResponse;
import ma.salman.sbschoolassojet.dto.auth.LoginRequest;
import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.models.Utilisateur;
import ma.salman.sbschoolassojet.repositories.UtilisateurRepository;
import ma.salman.sbschoolassojet.security.JwtUtils;
import ma.salman.sbschoolassojet.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurRepository utilisateurRepository;
    private final JwtUtils jwtUtils;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        System.out.println("Tentative de login pour: " + loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        System.out.println("Authentification réussie!");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        System.out.println("UserDetails récupéré, ID: " + userDetails.getId());

        Utilisateur utilisateur = utilisateurRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userDetails.getId()));

        return new JwtResponse(
                jwt,
                "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                userDetails.getEmail(),
                utilisateur.getRole().name()
        );
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
}