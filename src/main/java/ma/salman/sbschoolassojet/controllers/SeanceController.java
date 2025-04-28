package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.seance.SeanceRequest;
import ma.salman.sbschoolassojet.dto.seance.SeanceResponse;
import ma.salman.sbschoolassojet.enums.StatusSeance;
import ma.salman.sbschoolassojet.services.SeanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/seances")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SeanceController {
    private final SeanceService seanceService;

    public SeanceController(SeanceService seanceService) {
        this.seanceService = seanceService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<SeanceResponse>>> getAllSeances() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Séances récupérées avec succès",
                seanceService.getAllSeances(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<SeanceResponse>> getSeanceById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Séance récupérée avec succès",
                seanceService.getSeanceById(id),
                null
        ));
    }

    @GetMapping("/module/{moduleId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<SeanceResponse>>> getSeancesByModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Séances récupérées avec succès",
                seanceService.getSeancesByModule(moduleId),
                null
        ));
    }

    @GetMapping("/enseignant/{enseignantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<SeanceResponse>>> getSeancesByEnseignant(@PathVariable Long enseignantId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Séances récupérées avec succès",
                seanceService.getSeancesByEnseignant(enseignantId),
                null
        ));
    }

    @GetMapping("/statut/{statut}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<SeanceResponse>>> getSeancesByStatut(@PathVariable StatusSeance statut) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Séances récupérées avec succès",
                seanceService.getSeancesByStatut(statut),
                null
        ));
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<SeanceResponse>>> getSeancesByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Séances récupérées avec succès",
                seanceService.getSeancesByDate(date),
                null
        ));
    }
    @GetMapping("/enseignant/{enseignantId}/periode")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<SeanceResponse>>> getSeancesByEnseignantAndPeriode(
            @PathVariable Long enseignantId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Séances récupérées avec succès",
                seanceService.getSeancesByEnseignantAndPeriode(enseignantId, dateDebut, dateFin),
                null
        ));
    }

    @GetMapping("/classe/{classeId}/date/{date}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<List<SeanceResponse>>> getSeancesByClasseAndDate(
            @PathVariable Long classeId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Séances récupérées avec succès",
                seanceService.getSeancesByClasseAndDate(classeId, date),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<SeanceResponse>> createSeance(@Valid @RequestBody SeanceRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Séance créée avec succès",
                seanceService.createSeance(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<SeanceResponse>> updateSeance(
            @PathVariable Long id,
            @Valid @RequestBody SeanceRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Séance mise à jour avec succès",
                seanceService.updateSeance(id, request),
                null
        ));
    }

    @PutMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<SeanceResponse>> updateStatut(
            @PathVariable Long id,
            @RequestParam StatusSeance statut) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Statut de la séance mis à jour avec succès",
                seanceService.updateStatut(id, statut),
                null
        ));
    }

    @PutMapping("/{id}/activer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SeanceResponse>> activerSeance(
            @PathVariable Long id,
            @RequestParam boolean actif) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                actif ? "Séance activée avec succès" : "Séance désactivée avec succès",
                seanceService.activerSeance(id, actif),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteSeance(@PathVariable Long id) {
        seanceService.deleteSeance(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Séance supprimée avec succès",
                null,
                null
        ));
    }
}
