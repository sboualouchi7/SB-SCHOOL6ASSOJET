package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.classe.ClasseResponse;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.module.ModuleRequest;
import ma.salman.sbschoolassojet.dto.module.ModuleResponse;
import ma.salman.sbschoolassojet.dto.session.SessionResponse;
import ma.salman.sbschoolassojet.security.UserDetailsImpl;
import ma.salman.sbschoolassojet.services.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {
    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<ModuleResponse>>> getAllModules() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Modules récupérés avec succès",
                moduleService.getAllModules(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<ModuleResponse>> getModuleById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Module récupéré avec succès",
                moduleService.getModuleById(id),
                null
        ));
    }

    @GetMapping("/classe/{classeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<ModuleResponse>>> getModulesByClasse(@PathVariable Long classeId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Modules récupérés avec succès",
                moduleService.getModulesByClasse(classeId),
                null
        ));
    }

    @GetMapping("/enseignant/{enseignantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<ModuleResponse>>> getModulesByEnseignant(@PathVariable Long enseignantId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Modules récupérés avec succès",
                moduleService.getModulesByEnseignant(enseignantId),
                null
        ));
    }

    @GetMapping("/mes-modules")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<ModuleResponse>>> getModulesForCurrentEnseignant() {
        // Récupérer l'ID de l'enseignant connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long enseignantId = userDetails.getId();

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Mes modules récupérés avec succès",
                moduleService.getModulesByEnseignant(enseignantId),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ModuleResponse>> createModule(@Valid @RequestBody ModuleRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Module créé avec succès",
                moduleService.createModule(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ModuleResponse>> updateModule(
            @PathVariable Long id,
            @Valid @RequestBody ModuleRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Module mis à jour avec succès",
                moduleService.updateModule(id, request),
                null
        ));
    }

    @PutMapping("/{id}/activer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ModuleResponse>> activerModule(
            @PathVariable Long id,
            @RequestParam boolean actif) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                actif ? "Module activé avec succès" : "Module désactivé avec succès",
                moduleService.activerModule(id, actif),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Module supprimé avec succès",
                null,
                null
        ));
    }

    @GetMapping("/enseignant/module/{moduleId}/classes")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<ClasseResponse>>> getClassesByModuleForCurrentEnseignant(
            @PathVariable Long moduleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long enseignantId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Classes du module récupérées avec succès",
                moduleService.getClassesByModuleAndEnseignant(moduleId, enseignantId),
                null
        ));
    }

    @GetMapping("/session/{sessionId}/enseignant-modules")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<ModuleResponse>>> getModulesBySessionAndCurrentEnseignant(
            @PathVariable Long sessionId) {

        // Récupérer l'ID de l'enseignant connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long enseignantId = userDetails.getId();

        List<ModuleResponse> modules = moduleService.getModulesBySessionAndEnseignant(sessionId, enseignantId);

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Modules de l'enseignant pour la session récupérés avec succès",
                modules,
                null
        ));
    }

    @GetMapping("/enseignant/sessions")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<SessionResponse>>> getSessionsForCurrentEnseignant() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long enseignantId = userDetails.getId();

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Mes sessions récupérées avec succès",
                moduleService.getSessionsByEnseignant(enseignantId),
                null
        ));
    }
}