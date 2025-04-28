package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.absence.AbsenceRequest;
import ma.salman.sbschoolassojet.dto.absence.AbsenceResponse;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantResponse;
import ma.salman.sbschoolassojet.security.UserDetailsImpl;
import ma.salman.sbschoolassojet.services.AbsenceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/absences")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AbsenceController {
    private final AbsenceService absenceService;

    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<AbsenceResponse>>> getAllAbsences() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Absences récupérées avec succès",
                absenceService.getAllAbsences(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT') or @securityService.isEtudiantOrParentForAbsence(#id, authentication)")
    public ResponseEntity<ApiResponse<AbsenceResponse>> getAbsenceById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Absence récupérée avec succès",
                absenceService.getAbsenceById(id),
                null
        ));
    }

    @GetMapping("/etudiant/{etudiantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT') or @securityService.isEtudiantOrParent(#etudiantId, authentication)")
    public ResponseEntity<ApiResponse<List<AbsenceResponse>>> getAbsencesByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Absences récupérées avec succès",
                absenceService.getAbsencesByEtudiant(etudiantId),
                null
        ));
    }

    @GetMapping("/seance/{seanceId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<AbsenceResponse>>> getAbsencesBySeance(@PathVariable Long seanceId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Absences récupérées avec succès",
                absenceService.getAbsencesBySeance(seanceId),
                null
        ));
    }

    @GetMapping("/etudiant/{etudiantId}/periode")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT') or @securityService.isEtudiantOrParent(#etudiantId, authentication)")
    public ResponseEntity<ApiResponse<List<AbsenceResponse>>> getAbsencesByEtudiantAndPeriode(
            @PathVariable Long etudiantId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Absences récupérées avec succès",
                absenceService.getAbsencesByEtudiantAndPeriode(etudiantId, dateDebut, dateFin),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<AbsenceResponse>> createAbsence(@Valid @RequestBody AbsenceRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Absence créée avec succès",
                absenceService.createAbsence(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<AbsenceResponse>> updateAbsence(
            @PathVariable Long id,
            @Valid @RequestBody AbsenceRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Absence mise à jour avec succès",
                absenceService.updateAbsence(id, request),
                null
        ));
    }

    @PutMapping("/{id}/valider")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<AbsenceResponse>> validerAbsence(
            @PathVariable Long id,
            @RequestParam boolean validee) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                validee ? "Absence validée avec succès" : "Absence invalidée avec succès",
                absenceService.validerAbsence(id, validee),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<Void>> deleteAbsence(@PathVariable Long id) {
        absenceService.deleteAbsence(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Absence supprimée avec succès",
                null,
                null
        ));
    }

    // Nouveaux endpoints pour le flux d'enregistrement des absences

    @GetMapping("/module/{moduleId}/classe/{classeId}/etudiants")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<EtudiantResponse>>> getEtudiantsByModuleAndClasse(
            @PathVariable Long moduleId,
            @PathVariable Long classeId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long enseignantId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Étudiants du module et de la classe récupérés avec succès",
                absenceService.getEtudiantsByModuleClasseForEnseignant(moduleId, classeId, enseignantId),
                null
        ));
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public ResponseEntity<ApiResponse<List<AbsenceResponse>>> createAbsencesBulk(
            @Valid @RequestBody List<AbsenceRequest> requests) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long enseignantId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Absences enregistrées avec succès",
                absenceService.createAbsencesBulk(requests, enseignantId),
                null
        ));
    }
}