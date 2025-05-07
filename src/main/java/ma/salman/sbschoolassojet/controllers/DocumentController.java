package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.dto.document.DocumentRequest;
import ma.salman.sbschoolassojet.dto.document.DocumentResponse;
import ma.salman.sbschoolassojet.enums.StatusDocument;
import ma.salman.sbschoolassojet.enums.TypeDocument;
import ma.salman.sbschoolassojet.security.SecurityService;
import ma.salman.sbschoolassojet.security.UserDetailsImpl;
import ma.salman.sbschoolassojet.services.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DocumentController {
    private final DocumentService documentService;
    private final SecurityService securityService;

    public DocumentController(DocumentService documentService , SecurityService securityService) {
        this.documentService = documentService;
        this.securityService = securityService;
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
    /**
     * Crée une demande de document pour l'utilisateur connecté (étudiant, parent ou enseignant)
     * - Si l'utilisateur est un étudiant, la demande est automatiquement pour lui-même
     * - Si l'utilisateur est un parent, il doit spécifier l'ID de l'étudiant concerné
     * - Si l'utilisateur est un enseignant, la demande est pour lui-même
     */
    @PostMapping("/me")
    @PreAuthorize("hasAnyRole('ETUDIANT', 'PARENT', 'ENSEIGNANT')")
    public ResponseEntity<ApiResponse<DocumentResponse>> createDocumentForConnectedUser(
            @Valid @RequestBody DocumentRequest request) {

        // Récupérer les informations de l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        // Déterminer le rôle de l'utilisateur
        boolean isEtudiant = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ETUDIANT"));
        boolean isParent = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PARENT"));
        boolean isEnseignant = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ENSEIGNANT"));

        // Définir le demandeur comme l'utilisateur connecté
        request.setDemandeurId(userId);

        // Si l'utilisateur est un étudiant, forcer l'etudiantId à être son propre ID
        if (isEtudiant) {
            request.setEtudiantId(userId);
        }
        // Si l'utilisateur est un parent, vérifier qu'il a le droit de faire une demande pour cet étudiant
        else if (isParent) {
            Long etudiantId = request.getEtudiantId();
            if (etudiantId == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(
                        false,
                        "L'ID de l'étudiant est requis pour une demande faite par un parent",
                        null,
                        null
                ));
            }

            // Vérifier que l'étudiant est bien un enfant du parent
            if (!securityService.isEtudiantOrParent(etudiantId, authentication)) {
                return ResponseEntity.status(403).body(new ApiResponse<>(
                        false,
                        "Vous n'êtes pas autorisé à faire une demande pour cet étudiant",
                        null,
                        null
                ));
            }
        }
        // Si l'utilisateur est un enseignant, la demande est pour lui-même
        else if (isEnseignant) {
            // Pour les enseignants, l'etudiantId n'est pas applicable
            // On peut le mettre à null ou à une valeur spéciale selon votre modèle de données
            request.setEtudiantId(null); // ou une valeur par défaut spéciale
        }

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Document demandé avec succès",
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
