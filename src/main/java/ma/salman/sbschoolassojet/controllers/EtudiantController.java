package ma.salman.sbschoolassojet.controllers;

import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantRequest;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantResponse;
import ma.salman.sbschoolassojet.services.EtudiantService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EtudiantController {
    private final EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<EtudiantResponse>>> getAllEtudiants() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Étudiants récupérés avec succès",
                etudiantService.getAllEtudiants(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT') or hasRole('PARENT') or authentication.principal.id == #id")
    public ResponseEntity<ApiResponse<EtudiantResponse>> getEtudiantById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Étudiant récupéré avec succès",
                etudiantService.getEtudiantById(id),
                null
        ));
    }

    @GetMapping("/classe/{classeId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<EtudiantResponse>>> getEtudiantsByClasse(@PathVariable Long classeId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Étudiants récupérés avec succès",
                etudiantService.getEtudiantsByClasse(classeId),
                null
        ));
    }

    @GetMapping("/niveau/{niveauId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<EtudiantResponse>>> getEtudiantsByNiveau(@PathVariable Long niveauId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Étudiants récupérés avec succès",
                etudiantService.getEtudiantsByNiveau(niveauId),
                null
        ));
    }

    @GetMapping("/anneeScolaire/{anneeScolaire}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<EtudiantResponse>>> getEtudiantsByAnneeScolaire(@PathVariable String anneeScolaire) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Étudiants récupérés avec succès",
                etudiantService.getEtudiantsByAnneeScolaire(anneeScolaire),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EtudiantResponse>> createEtudiant(@Valid @RequestBody EtudiantRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Étudiant créé avec succès",
                etudiantService.createEtudiant(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ETUDIANT') and authentication.principal.id == #id)")
    public ResponseEntity<ApiResponse<EtudiantResponse>> updateEtudiant(
            @PathVariable Long id,
            @Valid @RequestBody EtudiantRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Étudiant mis à jour avec succès",
                etudiantService.updateEtudiant(id, request),
                null
        ));
    }

    @PutMapping("/{id}/activer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EtudiantResponse>> activerEtudiant(
            @PathVariable Long id,
            @RequestParam boolean actif) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                actif ? "Étudiant activé avec succès" : "Étudiant désactivé avec succès",
                etudiantService.activerEtudiant(id, actif),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteEtudiant(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Étudiant supprimé avec succès",
                null,
                null
        ));
    }

}
