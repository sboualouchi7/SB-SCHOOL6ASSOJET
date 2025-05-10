package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurRequest;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurResponse;
import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.services.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UtilisateurController {
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    @GetMapping
    public ResponseEntity<List<UtilisateurResponse>> getAllUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> getUtilisateurById(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.getUtilisateurById(id));
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UtilisateurResponse> createUtilisateur(
            @RequestPart("utilisateur") @Valid UtilisateurRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {
        System.out.println("Requête utilisateur reçue: " + request);
        System.out.println("Photo reçue: " + (photo != null ? photo.getOriginalFilename() : "aucune"));
        UtilisateurResponse response = utilisateurService.createUtilisateur(request, photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UtilisateurResponse> updateUtilisateur(
            @PathVariable Long id,
            @RequestPart("utilisateur") @Valid UtilisateurRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {

        return ResponseEntity.ok(utilisateurService.updateUtilisateur(id, request, photo));
    }

    @PatchMapping(value = "/{id}/photo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UtilisateurResponse> updateUtilisateurPhoto(
            @PathVariable Long id,
            @RequestPart("photo") MultipartFile photo) throws IOException {

        return ResponseEntity.ok(utilisateurService.updateUtilisateurPhoto(id, photo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UtilisateurResponse>> getUtilisateursByRole(@PathVariable Role role) {
        return ResponseEntity.ok(utilisateurService.getUtilisateursByRole(role));
    }
/**
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UtilisateurResponse>>> getAllUtilisateurs() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Utilisateurs récupérés avec succès",
                utilisateurService.getAllUtilisateurs(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<ApiResponse<UtilisateurResponse>> getUtilisateurById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Utilisateur récupéré avec succès",
                utilisateurService.getUtilisateurById(id),
                null
        ));
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UtilisateurResponse>>> getUtilisateursByRole(@PathVariable Role role) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Utilisateurs récupérés avec succès",
                utilisateurService.getUtilisateursByRole(role),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UtilisateurResponse>> createUtilisateur(@Valid @RequestBody UtilisateurRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Utilisateur créé avec succès",
                utilisateurService.createUtilisateur(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<ApiResponse<UtilisateurResponse>> updateUtilisateur(
            @PathVariable Long id,
            @Valid @RequestBody UtilisateurRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Utilisateur mis à jour avec succès",
                utilisateurService.updateUtilisateur(id, request),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Utilisateur supprimé avec succès",
                null,
                null
        ));
    }
**/
}
