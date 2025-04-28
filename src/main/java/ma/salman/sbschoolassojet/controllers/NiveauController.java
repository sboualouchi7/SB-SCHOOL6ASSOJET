package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.niveau.NiveauRequest;
import ma.salman.sbschoolassojet.dto.niveau.NiveauResponse;
import ma.salman.sbschoolassojet.services.NiveauService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/niveaux")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NiveauController {
    private final NiveauService niveauService;

    public NiveauController(NiveauService niveauService) {
        this.niveauService = niveauService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<NiveauResponse>>> getAllNiveaux() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Niveaux récupérés avec succès",
                niveauService.getAllNiveaux(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<NiveauResponse>> getNiveauById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Niveau récupéré avec succès",
                niveauService.getNiveauById(id),
                null
        ));
    }

    @GetMapping("/actifs")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<NiveauResponse>>> getNiveauxActifs() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Niveaux actifs récupérés avec succès",
                niveauService.getNiveauxActifs(),
                null
        ));
    }

    @GetMapping("/ordonnes")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<NiveauResponse>>> getNiveauxOrdonnes() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Niveaux ordonnés récupérés avec succès",
                niveauService.getNiveauxOrdonnes(),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NiveauResponse>> createNiveau(@Valid @RequestBody NiveauRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Niveau créé avec succès",
                niveauService.createNiveau(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NiveauResponse>> updateNiveau(
            @PathVariable Long id,
            @Valid @RequestBody NiveauRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Niveau mis à jour avec succès",
                niveauService.updateNiveau(id, request),
                null
        ));
    }

    @PutMapping("/{id}/activer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NiveauResponse>> activerNiveau(
            @PathVariable Long id,
            @RequestParam boolean actif) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                actif ? "Niveau activé avec succès" : "Niveau désactivé avec succès",
                niveauService.activerNiveau(id, actif),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteNiveau(@PathVariable Long id) {
        niveauService.deleteNiveau(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Niveau supprimé avec succès",
                null,
                null
        ));
    }
}
