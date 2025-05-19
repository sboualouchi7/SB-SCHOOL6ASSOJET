package ma.salman.sbschoolassojet.controllers;



import lombok.RequiredArgsConstructor;

import ma.salman.sbschoolassojet.dto.Examen.ExamenRequestDTO;
import ma.salman.sbschoolassojet.dto.Examen.ExamenResponseDTO;
import ma.salman.sbschoolassojet.enums.TypeExamen;
import ma.salman.sbschoolassojet.services.ExamenService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/examens")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExamenController {

    private final ExamenService examenService;

    @GetMapping
    public ResponseEntity<List<ExamenResponseDTO>> getAllExamens() {
        List<ExamenResponseDTO> examens = examenService.getAllExamens();
        return ResponseEntity.ok(examens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamenResponseDTO> getExamenById(@PathVariable Integer id) {
        ExamenResponseDTO examen = examenService.getExamenById(id);
        return ResponseEntity.ok(examen);
    }

    @GetMapping("/classe/{classeId}")
    public ResponseEntity<List<ExamenResponseDTO>> getExamensByClasseId(@PathVariable Long classeId) {
        List<ExamenResponseDTO> examens = examenService.getExamensByClasseId(classeId);
        return ResponseEntity.ok(examens);
    }

    @GetMapping("/type/{typeExamen}")
    public ResponseEntity<List<ExamenResponseDTO>> getExamensByType(
            @PathVariable TypeExamen typeExamen) {
        List<ExamenResponseDTO> examens = examenService.getExamensByType(typeExamen);
        return ResponseEntity.ok(examens);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ExamenResponseDTO>> getExamensByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        List<ExamenResponseDTO> examens = examenService.getExamensByDateRange(debut, fin);
        return ResponseEntity.ok(examens);
    }

    @PostMapping
    public ResponseEntity<ExamenResponseDTO> createExamen(@Valid @RequestBody ExamenRequestDTO examenDTO) {
        ExamenResponseDTO createdExamen = examenService.createExamen(examenDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExamen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamenResponseDTO> updateExamen(
            @PathVariable Integer id,
            @Valid @RequestBody ExamenRequestDTO examenDTO) {
        ExamenResponseDTO updatedExamen = examenService.updateExamen(id, examenDTO);
        return ResponseEntity.ok(updatedExamen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable Integer id) {
        examenService.deleteExamen(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-exists")
    public ResponseEntity<Boolean> checkExamenExistsForClasseAndType(
            @RequestParam Long classeId,
            @RequestParam TypeExamen typeExamen) {
        boolean exists = examenService.isExamenExistsForClasseAndType(classeId, typeExamen);
        return ResponseEntity.ok(exists);
    }

    // Endpoint supplémentaire pour gérer la planification des examens
    @PostMapping("/planifier")
    public ResponseEntity<ExamenResponseDTO> planifierExamen(@Valid @RequestBody ExamenRequestDTO examenDTO) {
        // On pourrait ajouter ici une validation supplémentaire avant de créer l'examen
        // Par exemple, vérifier les disponibilités, conflits, etc.
        return createExamen(examenDTO);
    }
}