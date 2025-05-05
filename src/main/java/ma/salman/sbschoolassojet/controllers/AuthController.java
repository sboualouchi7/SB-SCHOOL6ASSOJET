package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.auth.JwtResponse;
import ma.salman.sbschoolassojet.dto.auth.LoginRequest;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.models.Utilisateur;
import ma.salman.sbschoolassojet.repositories.UtilisateurRepository;
import ma.salman.sbschoolassojet.security.JwtUtils;
import ma.salman.sbschoolassojet.security.UserDetailsImpl;
import ma.salman.sbschoolassojet.security.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurRepository utilisateurRepository;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("Tentative de login pour: " + loginRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            System.out.println("Authentification réussie!");

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            System.out.println("UserDetails récupéré, ID: " + userDetails.getId());

            Utilisateur utilisateur = utilisateurRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userDetails.getId()));

            return ResponseEntity.ok(new JwtResponse(
                    jwt,
                    "Bearer",
                    userDetails.getId(),
                    userDetails.getUsername(),
                    utilisateur.getNom(),
                    utilisateur.getPrenom(),
                    userDetails.getEmail(),
                    utilisateur.getRole().name()
            ));
        } catch (Exception e) {
            System.err.println("Erreur d'authentification: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(401).body(new ApiResponse<>(
                    false,
                    "Erreur d'authentification: " + e.getMessage(),
                    null,
                    null
            ));
        }
    }
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Utilisateur utilisateur = utilisateurRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userDetails.getId()));

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Token valide",
                utilisateur.getRole().name(),
                null
        ));
    }

    @GetMapping("/utilisateur/{username}/exists")
    public ResponseEntity<?> checkUsernameExists(@PathVariable String username) {
        boolean exists = utilisateurRepository.existsByUsername(username);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Vérification terminée",
                exists,
                null
        ));
    }

    @GetMapping("/email/{email}/exists")
    public ResponseEntity<ApiResponse> checkEmailExists(@PathVariable String email) {
        boolean exists = utilisateurRepository.existsByEmail(email);
        var response = new ApiResponse<>(
                true,
                "Vérification terminée",
                exists,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Rôles récupérés avec succès",
                Role.values(),
                null
        ));
    }
}