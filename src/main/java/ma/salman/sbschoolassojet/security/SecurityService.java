package ma.salman.sbschoolassojet.security;

import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.models.*;
import ma.salman.sbschoolassojet.repositories.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Service de sécurité personnalisé qui gère les autorisations d'accès aux ressources
 * en fonction des rôles et des relations entre les utilisateurs.
 * Ce service est utilisé principalement dans les annotations @PreAuthorize pour
 * implémenter des règles d'autorisation complexes qui vont au-delà de la simple
 * vérification des rôles.
 */
@Service
@RequiredArgsConstructor
public class SecurityService {
    private final EtudiantRepository etudiantRepository;
    private final ParentRepository parentRepository;
    private final AbsenceRepository absenceRepository;
    private final EvaluationRepository evaluationRepository;
    private final DocumentRepository documentRepository;

    /**
     * Vérifie si l'utilisateur authentifié est un administrateur, un enseignant,
     * l'étudiant lui-même, ou un parent de l'étudiant.
     *
     * @param etudiantId ID de l'étudiant dont on veut accéder aux informations
     * @param authentication Objet Authentication de Spring Security contenant les informations de l'utilisateur connecté
     * @return true si l'accès est autorisé, false sinon
     */
    public boolean isEtudiantOrParent(Long etudiantId, Authentication authentication) {
        // Les administrateurs et enseignants ont accès à toutes les ressources
        if (hasAdminOrEnseignantRole(authentication)) {
            return true;
        }

        // Extraction de l'ID de l'utilisateur authentifié
        Long authUserId = extractUserIdFromAuthentication(authentication);

        // Vérification si l'utilisateur est l'étudiant lui-même
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ETUDIANT"))) {
            return etudiantId.equals(authUserId);
        }

        // Vérification si l'utilisateur est un parent de l'étudiant
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PARENT"))) {
            return isParentOfStudent(authUserId, etudiantId);
        }

        return false;
    }

    /**
     * Vérifie si l'utilisateur authentifié a le droit d'accéder à une absence spécifique.
     * Un utilisateur peut accéder à une absence s'il est admin, enseignant, l'étudiant concerné,
     * ou un parent de l'étudiant concerné.
     *
     * @param absenceId ID de l'absence
     * @param authentication Objet Authentication de Spring Security
     * @return true si l'accès est autorisé, false sinon
     */
    public boolean isEtudiantOrParentForAbsence(Long absenceId, Authentication authentication) {
        // Les administrateurs et enseignants ont accès à toutes les absences
        if (hasAdminOrEnseignantRole(authentication)) {
            return true;
        }

        // Récupération de l'absence et vérification des droits
        Optional<Absence> absence = absenceRepository.findById(absenceId);
        if (absence.isPresent()) {
            return isEtudiantOrParent(absence.get().getEtudiantId(), authentication);
        }

        return false;
    }

    /**
     * Vérifie si l'utilisateur authentifié a le droit d'accéder à une évaluation spécifique.
     * Un utilisateur peut accéder à une évaluation s'il est admin, enseignant, l'étudiant concerné,
     * ou un parent de l'étudiant concerné.
     *
     * @param evaluationId ID de l'évaluation
     * @param authentication Objet Authentication de Spring Security
     * @return true si l'accès est autorisé, false sinon
     */
    public boolean isEtudiantOrParentForEvaluation(Long evaluationId, Authentication authentication) {
        // Les administrateurs et enseignants ont accès à toutes les évaluations
        if (hasAdminOrEnseignantRole(authentication)) {
            return true;
        }

        // Récupération de l'évaluation et vérification des droits
        Optional<Evaluation> evaluation = evaluationRepository.findById(evaluationId);
        if (evaluation.isPresent()) {
            return isEtudiantOrParent(evaluation.get().getEtudiantId(), authentication);
        }

        return false;
    }

    /**
     * Vérifie si l'utilisateur authentifié est le demandeur d'un document spécifique
     * ou s'il est administrateur.
     *
     * @param documentId ID du document
     * @param authentication Objet Authentication de Spring Security
     * @return true si l'accès est autorisé, false sinon
     */
    public boolean isDocumentDemandeur(Long documentId, Authentication authentication) {
        // Les administrateurs ont accès à tous les documents
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return true;
        }

        // Extraction de l'ID de l'utilisateur authentifié
        Long authUserId = extractUserIdFromAuthentication(authentication);

        // Vérification si l'utilisateur est le demandeur du document
        Optional<Document> document = documentRepository.findById(documentId);
        if (document.isPresent()) {
            return document.get().getDemandeurId().equals(authUserId);
        }

        return false;
    }

    /**
     * Méthode utilitaire pour vérifier si l'utilisateur a un rôle d'admin ou d'enseignant.
     *
     * @param authentication Objet Authentication de Spring Security
     * @return true si l'utilisateur a un rôle admin ou enseignant, false sinon
     */
    private boolean hasAdminOrEnseignantRole(Authentication authentication) {
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ENSEIGNANT"));
    }

    /**
     * Méthode utilitaire pour extraire l'ID de l'utilisateur à partir de l'objet Authentication.
     * Dans votre système, le nom d'utilisateur semble être au format "ROLE_ID".
     *
     * @param authentication Objet Authentication de Spring Security
     * @return L'ID de l'utilisateur authentifié
     */
    private Long extractUserIdFromAuthentication(Authentication authentication) {
        return Long.parseLong(authentication.getName().split("_")[1]);
    }

    /**
     * Vérifie si un parent (identifié par son ID) est bien le parent d'un étudiant spécifique.
     *
     * @param parentId ID du parent
     * @param etudiantId ID de l'étudiant
     * @return true si le parent est bien un parent de l'étudiant, false sinon
     */
    private boolean isParentOfStudent(Long parentId, Long etudiantId) {
        Optional<Parent> parent = parentRepository.findById(parentId);
        if (parent.isPresent()) {
            Set<Long> enfantsIds = parent.get().getEnfants().stream()
                    .map(Etudiant::getId)
                    .collect(Collectors.toSet());
            return enfantsIds.contains(etudiantId);
        }
        return false;
    }
}