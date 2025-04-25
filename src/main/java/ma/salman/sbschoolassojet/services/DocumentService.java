package ma.salman.sbschoolassojet.services;

import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.document.DocumentRequest;
import ma.salman.sbschoolassojet.dto.document.DocumentResponse;
import ma.salman.sbschoolassojet.enums.StatusDocument;
import ma.salman.sbschoolassojet.enums.TypeDocument;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.DocumentMapper;
import ma.salman.sbschoolassojet.models.Document;
import ma.salman.sbschoolassojet.models.Etudiant;
import ma.salman.sbschoolassojet.models.Utilisateur;
import ma.salman.sbschoolassojet.repositories.DocumentRepository;
import ma.salman.sbschoolassojet.repositories.EtudiantRepository;
import ma.salman.sbschoolassojet.repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final EtudiantRepository etudiantRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final DocumentMapper documentMapper;

    public List<DocumentResponse> getAllDocuments() {
        return documentRepository.findAll().stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }

    public DocumentResponse getDocumentById(Long id) {
        return documentRepository.findById(id)
                .map(documentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Document non trouvé avec l'ID: " + id));
    }

    public List<DocumentResponse> getDocumentsByEtudiant(Long etudiantId) {
        return documentRepository.findByEtudiantId(etudiantId).stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<DocumentResponse> getDocumentsByDemandeur(Long demandeurId) {
        return documentRepository.findByDemandeurId(demandeurId).stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<DocumentResponse> getDocumentsByType(TypeDocument type) {
        return documentRepository.findByType(type).stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<DocumentResponse> getDocumentsByStatus(StatusDocument status) {
        return documentRepository.findByStatus(status).stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public DocumentResponse createDocument(DocumentRequest request) {
        Document document = documentMapper.toEntity(request);

        // Vérifier que l'étudiant existe
       Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
               .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + request.getEtudiantId()));

        // Vérifier que le demandeur existe
        Utilisateur demandeur = utilisateurRepository.findById(request.getDemandeurId())
                .orElseThrow(() -> new ResourceNotFoundException("Demandeur non trouvé avec l'ID: " + request.getDemandeurId()));
       document.setEtudiant(etudiant);
        document.setDemandeur(demandeur);

        return documentMapper.toDto(documentRepository.save(document));
    }

    @Transactional
    public DocumentResponse updateDocument(Long id, DocumentRequest request) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document non trouvé avec l'ID: " + id));

        documentMapper.updateEntityFromDto(request, document);

        // Vérifier que l'étudiant existe s'il est modifié
        if (request.getEtudiantId() != null && !request.getEtudiantId().equals(document.getEtudiantId())) {
            Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + request.getEtudiantId()));
        }

        // Vérifier que le demandeur existe s'il est modifié
        if (request.getDemandeurId() != null && !request.getDemandeurId().equals(document.getDemandeurId())) {
            Utilisateur demandeur = utilisateurRepository.findById(request.getDemandeurId())
                    .orElseThrow(() -> new ResourceNotFoundException("Demandeur non trouvé avec l'ID: " + request.getDemandeurId()));
        }

        return documentMapper.toDto(documentRepository.save(document));
    }

    @Transactional
    public DocumentResponse updateStatus(Long id, StatusDocument status) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document non trouvé avec l'ID: " + id));

        document.setStatus(status);

        if (status == StatusDocument.PRET) {
            document.setDateDelivrance(LocalDate.now());
        }

        return documentMapper.toDto(documentRepository.save(document));
    }

    @Transactional
    public DocumentResponse updateFichierUrl(Long id, String fichierUrl) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document non trouvé avec l'ID: " + id));

        document.setFichierUrl(fichierUrl);
        return documentMapper.toDto(documentRepository.save(document));
    }

    @Transactional
    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Document non trouvé avec l'ID: " + id);
        }
        documentRepository.deleteById(id);
    }


    /*
    affecter directement le id du user connecte au demande de doc
    @Transactional
public DocumentResponse createDocument(DocumentRequest request) {
    // Récupérer l'utilisateur authentifié actuel
    Utilisateur demandeurActuel = getCurrentAuthenticatedUser();

    // Créer le document à partir de la requête
    Document document = documentMapper.toEntity(request);

    // Vérifier que l'étudiant existe
    Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
            .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + request.getEtudiantId()));

    // Associer l'étudiant et le demandeur (utilisateur authentifié) au document
    document.setEtudiant(etudiant);
    document.setDemandeur(demandeurActuel);

    return documentMapper.toDto(documentRepository.save(document));
}

// Méthode pour récupérer l'utilisateur authentifié actuel
private Utilisateur getCurrentAuthenticatedUser() {
    // Si vous utilisez Spring Security
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    return utilisateurRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur authentifié non trouvé"));
}*/
}
