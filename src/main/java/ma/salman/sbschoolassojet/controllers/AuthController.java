package ma.salman.sbschoolassojet.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.auth.JwtResponse;
import ma.salman.sbschoolassojet.dto.auth.LoginRequest;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse response = authService.authenticateUser(loginRequest);
            return ResponseEntity.ok(response);
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
        try {
            String role = authService.verifyToken();
            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    "Token valide",
                    role,
                    null
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ApiResponse<>(
                    false,
                    "Token invalide: " + e.getMessage(),
                    null,
                    null
            ));
        }
    }

    @GetMapping("/utilisateur/{username}/exists")
    public ResponseEntity<?> checkUsernameExists(@PathVariable String username) {
        boolean exists = authService.checkUsernameExists(username);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Vérification terminée",
                exists,
                null
        ));
    }

    @GetMapping("/email/{email}/exists")
    public ResponseEntity<ApiResponse> checkEmailExists(@PathVariable String email) {
        boolean exists = authService.checkEmailExists(email);
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
                authService.getRoles(),
                null
        ));
    }
}