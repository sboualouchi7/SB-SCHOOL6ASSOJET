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
@Service
@RequiredArgsConstructor
public class SecurityService {
    private final EtudiantRepository etudiantRepository;
    private final ParentRepository parentRepository;
    private final  AbsenceRepository absenceRepository;
    private final EvaluationRepository evaluationRepository;
    private final DocumentRepository documentRepository;

    public boolean isEtudiantOrParent(Long etudiantId, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ENSEIGNANT"))) {
            return true;
        }

        Long authUserId = Long.parseLong(authentication.getName().split("_")[1]);

        // Vérifier si l'utilisateur authentifié est l'étudiant lui-même
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ETUDIANT"))) {
            return etudiantId.equals(authUserId);
        }

        // Vérifier si l'utilisateur authentifié est un parent de l'étudiant
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PARENT"))) {
            Optional<Parent> parent = parentRepository.findById(authUserId);
            if (parent.isPresent()) {
                Set<Long> enfantsIds = parent.get().getEnfants().stream()
                        .map(Etudiant::getId)
                        .collect(Collectors.toSet());
                return enfantsIds.contains(etudiantId);
            }
        }

        return false;
    }

    public boolean isEtudiantOrParentForAbsence(Long absenceId, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ENSEIGNANT"))) {
            return true;
        }

        Optional<Absence> absence = absenceRepository.findById(absenceId);
        if (absence.isPresent()) {
            return isEtudiantOrParent(absence.get().getEtudiantId(), authentication);
        }

        return false;
    }

    public boolean isEtudiantOrParentForEvaluation(Long evaluationId, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ENSEIGNANT"))) {
            return true;
        }

        Optional<Evaluation> evaluation = evaluationRepository.findById(evaluationId);
        if (evaluation.isPresent()) {
            return isEtudiantOrParent(evaluation.get().getEtudiantId(), authentication);
        }

        return false;
    }

    public boolean isDocumentDemandeur(Long documentId, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return true;
        }

        Long authUserId = Long.parseLong(authentication.getName().split("_")[1]);

        Optional<Document> document = documentRepository.findById(documentId);
        if (document.isPresent()) {
            return document.get().getDemandeurId().equals(authUserId);
        }

        return false;
    }
}
