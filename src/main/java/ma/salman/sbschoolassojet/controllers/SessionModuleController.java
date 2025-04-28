package ma.salman.sbschoolassojet.controllers;

import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.sessionmodule.SessionModuleRequest;
import ma.salman.sbschoolassojet.dto.sessionmodule.SessionModuleResponse;
import ma.salman.sbschoolassojet.services.SessionModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session-modules")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SessionModuleController {
    private final SessionModuleService sessionModuleService;

    public SessionModuleController(SessionModuleService sessionModuleService) {
        this.sessionModuleService = sessionModuleService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<SessionModuleResponse>>> getAllSessionModules() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "SessionModules récupérés avec succès",
                sessionModuleService.getAllSessionModules(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<SessionModuleResponse>> getSessionModuleById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "SessionModule récupéré avec succès",
                sessionModuleService.getSessionModuleById(id),
                null
        ));
    }

    @GetMapping("/session/{sessionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<SessionModuleResponse>>> getSessionModulesBySession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "SessionModules récupérés avec succès",
                sessionModuleService.getSessionModulesBySession(sessionId),
                null
        ));
    }

    @GetMapping("/module/{moduleId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<SessionModuleResponse>>> getSessionModulesByModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "SessionModules récupérés avec succès",
                sessionModuleService.getSessionModulesByModule(moduleId),
                null
        ));
    }

    @GetMapping("/session/{sessionId}/ordered")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<SessionModuleResponse>>> getSessionModulesBySessionOrdered(@PathVariable Long sessionId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "SessionModules récupérés avec succès",
                sessionModuleService.getSessionModulesBySessionOrdered(sessionId),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SessionModuleResponse>> createSessionModule(@Valid @RequestBody SessionModuleRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "SessionModule créé avec succès",
                sessionModuleService.createSessionModule(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SessionModuleResponse>> updateSessionModule(
            @PathVariable Long id,
            @Valid @RequestBody SessionModuleRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "SessionModule mis à jour avec succès",
                sessionModuleService.updateSessionModule(id, request),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteSessionModule(@PathVariable Long id) {
        sessionModuleService.deleteSessionModule(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "SessionModule supprimé avec succès",
                null,
                null
        ));
    }
}
