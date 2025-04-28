package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.session.SessionRequest;
import ma.salman.sbschoolassojet.dto.session.SessionResponse;
import ma.salman.sbschoolassojet.enums.StatutSession;
import ma.salman.sbschoolassojet.services.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<SessionResponse>>> getAllSessions() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Sessions récupérées avec succès",
                sessionService.getAllSessions(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<SessionResponse>> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Session récupérée avec succès",
                sessionService.getSessionById(id),
                null
        ));
    }

    @GetMapping("/responsable/{responsableId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<SessionResponse>>> getSessionsByResponsable(@PathVariable Long responsableId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Sessions récupérées avec succès",
                sessionService.getSessionsByResponsable(responsableId),
                null
        ));
    }

    @GetMapping("/anneeScolaire/{anneeScolaire}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<SessionResponse>>> getSessionsByAnneeScolaire(@PathVariable String anneeScolaire) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Sessions récupérées avec succès",
                sessionService.getSessionsByAnneeScolaire(anneeScolaire),
                null
        ));
    }

    @GetMapping("/statut/{statut}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<SessionResponse>>> getSessionsByStatut(@PathVariable StatutSession statut) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Sessions récupérées avec succès",
                sessionService.getSessionsByStatut(statut),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SessionResponse>> createSession(@Valid @RequestBody SessionRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Session créée avec succès",
                sessionService.createSession(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SessionResponse>> updateSession(
            @PathVariable Long id,
            @Valid @RequestBody SessionRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Session mise à jour avec succès",
                sessionService.updateSession(id, request),
                null
        ));
    }

    @PutMapping("/{id}/statut")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SessionResponse>> updateStatut(
            @PathVariable Long id,
            @RequestParam StatutSession statut) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Statut de la session mis à jour avec succès",
                sessionService.updateStatut(id, statut),
                null
        ));
    }

    @PutMapping("/{id}/activer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SessionResponse>> activerSession(
            @PathVariable Long id,
            @RequestParam boolean actif) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                actif ? "Session activée avec succès" : "Session désactivée avec succès",
                sessionService.activerSession(id, actif),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Session supprimée avec succès",
                null,
                null
        ));
    }
}
