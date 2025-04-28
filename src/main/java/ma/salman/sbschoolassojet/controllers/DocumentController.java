package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.document.DocumentRequest;
import ma.salman.sbschoolassojet.dto.document.DocumentResponse;
import ma.salman.sbschoolassojet.enums.StatusDocument;
import ma.salman.sbschoolassojet.enums.TypeDocument;
import ma.salman.sbschoolassojet.services.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getAllDocuments() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Documents récupérés avec succès",
                documentService.getAllDocuments(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT', 'PARENT')")
    public ResponseEntity<ApiResponse<DocumentResponse>> getDocumentById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Document récupéré avec succès",
                documentService.getDocumentById(id),
                null
        ));
    }

    @GetMapping("/etudiant/{etudiantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT') or @securityService.isEtudiantOrParent(#etudiantId, authentication)")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getDocumentsByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Documents récupérés avec succès",
                documentService.getDocumentsByEtudiant(etudiantId),
                null
        ));
    }

    @GetMapping("/demandeur/{demandeurId}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #demandeurId")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getDocumentsByDemandeur(@PathVariable Long demandeurId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Documents récupérés avec succès",
                documentService.getDocumentsByDemandeur(demandeurId),
                null
        ));
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getDocumentsByType(@PathVariable TypeDocument type) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Documents récupérés avec succès",
                documentService.getDocumentsByType(type),
                null
        ));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getDocumentsByStatus(@PathVariable StatusDocument status) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Documents récupérés avec succès",
                documentService.getDocumentsByStatus(status),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'PARENT', 'ETUDIANT')")
    public ResponseEntity<ApiResponse<DocumentResponse>> createDocument(@Valid @RequestBody DocumentRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Document créé avec succès",
                documentService.createDocument(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isDocumentDemandeur(#id, authentication)")
    public ResponseEntity<ApiResponse<DocumentResponse>> updateDocument(
            @PathVariable Long id,
            @Valid @RequestBody DocumentRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Document mis à jour avec succès",
                documentService.updateDocument(id, request),
                null
        ));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DocumentResponse>> updateStatus(
            @PathVariable Long id,
            @RequestParam StatusDocument status) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Statut du document mis à jour avec succès",
                documentService.updateStatus(id, status),
                null
        ));
}
}
