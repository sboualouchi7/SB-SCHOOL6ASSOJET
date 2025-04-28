package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.parent.ParentRequest;
import ma.salman.sbschoolassojet.dto.parent.ParentResponse;
import ma.salman.sbschoolassojet.services.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ParentResponse>>> getAllParents() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Parents récupérés avec succès",
                parentService.getAllParents(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PARENT') and authentication.principal.id == #id)")
    public ResponseEntity<ApiResponse<ParentResponse>> getParentById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Parent récupéré avec succès",
                parentService.getParentById(id),
                null
        ));
    }

    @GetMapping("/etudiant/{etudiantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT') or hasRole('PARENT')")
    public ResponseEntity<ApiResponse<List<ParentResponse>>> getParentsByEtudiantId(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Parents récupérés avec succès",
                parentService.getParentsByEtudiantId(etudiantId),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ParentResponse>> createParent(@Valid @RequestBody ParentRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Parent créé avec succès",
                parentService.createParent(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PARENT') and authentication.principal.id == #id)")
    public ResponseEntity<ApiResponse<ParentResponse>> updateParent(
            @PathVariable Long id,
            @Valid @RequestBody ParentRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Parent mis à jour avec succès",
                parentService.updateParent(id, request),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Parent supprimé avec succès",
                null,
                null
        ));
    }
}

