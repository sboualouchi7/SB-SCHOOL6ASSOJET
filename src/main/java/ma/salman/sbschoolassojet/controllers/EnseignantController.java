package ma.salman.sbschoolassojet.controllers;

import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.enseignant.EnseignantRequest;
import ma.salman.sbschoolassojet.dto.enseignant.EnseignantResponse;
import ma.salman.sbschoolassojet.services.EnseignantService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EnseignantController {
    private final EnseignantService enseignantService;

    public EnseignantController(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<EnseignantResponse>>> getAllEnseignants() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Enseignants récupérés avec succès",
                enseignantService.getAllEnseignants(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT') or authentication.principal.id == #id")
    public ResponseEntity<ApiResponse<EnseignantResponse>> getEnseignantById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Enseignant récupéré avec succès",
                enseignantService.getEnseignantById(id),
                null
        ));
    }

    @GetMapping("/departement/{departementId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<EnseignantResponse>>> getEnseignantsByDepartement(@PathVariable Long departementId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Enseignants récupérés avec succès",
                enseignantService.getEnseignantsByDepartement(departementId),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EnseignantResponse>> createEnseignant(@Valid @RequestBody EnseignantRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Enseignant créé avec succès",
                enseignantService.createEnseignant(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ENSEIGNANT') and authentication.principal.id == #id)")
    public ResponseEntity<ApiResponse<EnseignantResponse>> updateEnseignant(
            @PathVariable Long id,
            @Valid @RequestBody EnseignantRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Enseignant mis à jour avec succès",
                enseignantService.updateEnseignant(id, request),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteEnseignant(@PathVariable Long id) {
        enseignantService.deleteEnseignant(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Enseignant supprimé avec succès",
                null,
                null
        ));
    }
}
