package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.classe.ClasseRequest;
import ma.salman.sbschoolassojet.dto.classe.ClasseResponse;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.security.UserDetailsImpl;
import ma.salman.sbschoolassojet.services.ClasseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClasseController {
    private final ClasseService classeService;

    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<ClasseResponse>>> getAllClasses() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Classes récupérées avec succès",
                classeService.getAllClasses(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<ClasseResponse>> getClasseById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Classe récupérée avec succès",
                classeService.getClasseById(id),
                null
        ));
    }

    @GetMapping("/niveau/{niveauId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<ClasseResponse>>> getClassesByNiveau(@PathVariable Long niveauId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Classes récupérées avec succès",
                classeService.getClassesByNiveau(niveauId),
                null
        ));
    }

    @GetMapping("/anneeScolaire/{anneeScolaire}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<ClasseResponse>>> getClassesByAnneeScolaire(@PathVariable String anneeScolaire) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Classes récupérées avec succès",
                classeService.getClassesByAnneeScolaire(anneeScolaire),
                null
        ));
    }
    /**
     * Récupère les classes de l'enseignant authentifié
     * @return ResponseEntity contenant la liste des classes de l'enseignant
     */
    @GetMapping("/enseignant/me")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<ClasseResponse>>> getMyClasses() {
        // Récupérer l'ID de l'enseignant connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long enseignantId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Mes classes récupérées avec succès",
                classeService.getClassesByEnseignant(enseignantId),
                null
        ));
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClasseResponse>> createClasse(@Valid @RequestBody ClasseRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Classe créée avec succès",
                classeService.createClasse(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClasseResponse>> updateClasse(
            @PathVariable Long id,
            @Valid @RequestBody ClasseRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Classe mise à jour avec succès",
                classeService.updateClasse(id, request),
                null
        ));
    }

    @PutMapping("/{id}/activer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClasseResponse>> activerClasse(
            @PathVariable Long id,
            @RequestParam boolean actif) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                actif ? "Classe activée avec succès" : "Classe désactivée avec succès",
                classeService.activerClasse(id, actif),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Classe supprimée avec succès",
                null,
                null
        ));
    }
}
