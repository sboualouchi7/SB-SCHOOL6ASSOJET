package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantResponse;
import ma.salman.sbschoolassojet.dto.evaluation.EvaluationRequest;
import ma.salman.sbschoolassojet.dto.evaluation.EvaluationResponse;
import ma.salman.sbschoolassojet.enums.TypeEvaluation;
import ma.salman.sbschoolassojet.services.EvaluationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EvaluationController {
    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<EvaluationResponse>>> getAllEvaluations() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Évaluations récupérées avec succès",
                evaluationService.getAllEvaluations(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT') or @securityService.isEtudiantOrParentForEvaluation(#id, authentication)")
    public ResponseEntity<ApiResponse<EvaluationResponse>> getEvaluationById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Évaluation récupérée avec succès",
                evaluationService.getEvaluationById(id),
                null
        ));
    }

    @GetMapping("/etudiant/{etudiantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT') or @securityService.isEtudiantOrParent(#etudiantId, authentication)")
    public ResponseEntity<ApiResponse<List<EvaluationResponse>>> getEvaluationsByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Évaluations récupérées avec succès",
                evaluationService.getEvaluationsByEtudiant(etudiantId),
                null
        ));
    }

    @GetMapping("/module/{moduleId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<EvaluationResponse>>> getEvaluationsByModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Évaluations récupérées avec succès",
                evaluationService.getEvaluationsByModule(moduleId),
                null
        ));
    }

    @GetMapping("/enseignant/{enseignantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<EvaluationResponse>>> getEvaluationsByEnseignant(@PathVariable Long enseignantId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Évaluations récupérées avec succès",
                evaluationService.getEvaluationsByEnseignant(enseignantId),
                null
        ));
    }

    @GetMapping("/session/{sessionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<EvaluationResponse>>> getEvaluationsBySession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Évaluations récupérées avec succès",
                evaluationService.getEvaluationsBySession(sessionId),
                null
        ));
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<EvaluationResponse>>> getEvaluationsByType(@PathVariable TypeEvaluation type) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Évaluations récupérées avec succès",
                evaluationService.getEvaluationsByType(type),
                null
        ));
    }

    @GetMapping("/etudiant/{etudiantId}/module/{moduleId}/moyenne")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT') or @securityService.isEtudiantOrParent(#etudiantId, authentication)")
    public ResponseEntity<ApiResponse<Float>> getMoyenneByEtudiantAndModule(
            @PathVariable Long etudiantId,
            @PathVariable Long moduleId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Moyenne récupérée avec succès",
                evaluationService.getMoyenneByEtudiantAndModule(etudiantId, moduleId),
                null
        ));
    }

    @GetMapping("/module/{moduleId}/session/{sessionId}/moyenne")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<Float>> getMoyenneByModuleAndSession(
            @PathVariable Long moduleId,
            @PathVariable Long sessionId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Moyenne récupérée avec succès",
                evaluationService.getMoyenneByModuleAndSession(moduleId, sessionId),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<EvaluationResponse>> createEvaluation(@Valid @RequestBody EvaluationRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Évaluation créée avec succès",
                evaluationService.createEvaluation(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<EvaluationResponse>> updateEvaluation(
            @PathVariable Long id,
            @Valid @RequestBody EvaluationRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Évaluation mise à jour avec succès",
                evaluationService.updateEvaluation(id, request),
                null
        ));
    }

    @PutMapping("/{id}/valider")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<EvaluationResponse>> validerEvaluation(
            @PathVariable Long id,
            @RequestParam boolean estValidee) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                estValidee ? "Évaluation validée avec succès" : "Évaluation invalidée avec succès",
                evaluationService.validerEvaluation(id, estValidee),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<Void>> deleteEvaluation(@PathVariable Long id) {
        evaluationService.deleteEvaluation(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Évaluation supprimée avec succès",
                null,
                null
        ));
    }
    @GetMapping("/module/{moduleId}/session/{sessionId}/etudiants")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<EtudiantResponse>>> getEtudiantsByModuleAndSession(
            @PathVariable Long moduleId,
            @PathVariable Long sessionId) {

        List<EtudiantResponse> etudiants = evaluationService.getEtudiantsByModuleAndSession(moduleId, sessionId);

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Liste des étudiants récupérée avec succès",
                etudiants,
                null
        ));
    }
}
