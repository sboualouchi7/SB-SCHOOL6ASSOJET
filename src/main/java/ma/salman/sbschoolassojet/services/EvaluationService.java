package ma.salman.sbschoolassojet.services;
import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantResponse;
import ma.salman.sbschoolassojet.dto.evaluation.EvaluationRequest;
import ma.salman.sbschoolassojet.dto.evaluation.EvaluationResponse;
import ma.salman.sbschoolassojet.enums.TypeEvaluation;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.EtudiantMapper;
import ma.salman.sbschoolassojet.mappers.EvaluationMapper;
import ma.salman.sbschoolassojet.models.*;
import ma.salman.sbschoolassojet.models.Module;
import ma.salman.sbschoolassojet.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final EtudiantRepository etudiantRepository;
    private final ModuleRepository moduleRepository;
    private final EnseignantRepository enseignantRepository;
    private final SessionRepository sessionRepository;
    private final EvaluationMapper evaluationMapper;
    private final EtudiantMapper etudiantMapper;

    public List<EvaluationResponse> getAllEvaluations() {
        return evaluationRepository.findAll().stream()
                .map(evaluationMapper::toDto)
                .collect(Collectors.toList());
    }

    public EvaluationResponse getEvaluationById(Long id) {
        return evaluationRepository.findById(id)
                .map(evaluationMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation non trouvée avec l'ID: " + id));
    }

    public List<EvaluationResponse> getEvaluationsByEtudiant(Long etudiantId) {
        return evaluationRepository.findByEtudiantId(etudiantId).stream()
                .map(evaluationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EvaluationResponse> getEvaluationsByModule(Long moduleId) {
        return evaluationRepository.findByModuleId(moduleId).stream()
                .map(evaluationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EvaluationResponse> getEvaluationsByEnseignant(Long enseignantId) {
        return evaluationRepository.findByEnseignantId(enseignantId).stream()
                .map(evaluationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EvaluationResponse> getEvaluationsBySession(Long sessionId) {
        return evaluationRepository.findBySessionId(sessionId).stream()
                .map(evaluationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EvaluationResponse> getEvaluationsByType(TypeEvaluation type) {
        return evaluationRepository.findByType(type).stream()
                .map(evaluationMapper::toDto)
                .collect(Collectors.toList());
    }

    public Float getMoyenneByEtudiantAndModule(Long etudiantId, Long moduleId) {
        return evaluationRepository.findMoyenneByEtudiantIdAndModuleId(etudiantId, moduleId);
    }

    public Float getMoyenneByModuleAndSession(Long moduleId, Long sessionId) {
        return evaluationRepository.findMoyenneByModuleIdAndSessionId(moduleId, sessionId);
    }

    @Transactional
    public EvaluationResponse createEvaluation(EvaluationRequest request) {
        Evaluation evaluation = evaluationMapper.toEntity(request);

        // Vérifier que l'étudiant existe
        Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + request.getEtudiantId()));
        evaluation.setEtudiant(etudiant);  // Associer l'étudiant à l'évaluation

        // Vérifier que le module existe
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + request.getModuleId()));
        evaluation.setModule(module);  // Associer le module à l'évaluation

        // Vérifier que l'enseignant existe
        Enseignant enseignant = enseignantRepository.findById(request.getEnseignantId())
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant non trouvé avec l'ID: " + request.getEnseignantId()));
        evaluation.setEnseignant(enseignant);  // Associer l'enseignant à l'évaluation

        // Vérifier que la session existe
        Session session = sessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Session non trouvée avec l'ID: " + request.getSessionId()));
        evaluation.setSession(session);  // Associer la session à l'évaluation

        return evaluationMapper.toDto(evaluationRepository.save(evaluation));
    }

    @Transactional
    public EvaluationResponse updateEvaluation(Long id, EvaluationRequest request) {
        Evaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation non trouvée avec l'ID: " + id));

        evaluationMapper.updateEntityFromDto(request, evaluation);

        // Vérifier que l'étudiant existe s'il est modifié
        if (request.getEtudiantId() != null && !request.getEtudiantId().equals(evaluation.getEtudiantId())) {
            Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + request.getEtudiantId()));
            evaluation.setEtudiant(etudiant);  // Associer le nouvel étudiant
        }

        // Vérifier que le module existe s'il est modifié
        if (request.getModuleId() != null && !request.getModuleId().equals(evaluation.getModuleId())) {
            Module module = moduleRepository.findById(request.getModuleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + request.getModuleId()));
            evaluation.setModule(module);  // Associer le nouveau module
        }

        // Vérifier que l'enseignant existe s'il est modifié
        if (request.getEnseignantId() != null && !request.getEnseignantId().equals(evaluation.getEnseignantId())) {
            Enseignant enseignant = enseignantRepository.findById(request.getEnseignantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Enseignant non trouvé avec l'ID: " + request.getEnseignantId()));
            evaluation.setEnseignant(enseignant);  // Associer le nouvel enseignant
        }

        // Vérifier que la session existe si elle est modifiée
        if (request.getSessionId() != null && !request.getSessionId().equals(evaluation.getSessionId())) {
            Session session = sessionRepository.findById(request.getSessionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Session non trouvée avec l'ID: " + request.getSessionId()));
            evaluation.setSession(session);  // Associer la nouvelle session
        }

        return evaluationMapper.toDto(evaluationRepository.save(evaluation));
    }

    @Transactional
    public EvaluationResponse validerEvaluation(Long id, boolean estValidee) {
        Evaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation non trouvée avec l'ID: " + id));

        evaluation.setEstValidee(estValidee);
        return evaluationMapper.toDto(evaluationRepository.save(evaluation));
    }

    @Transactional
    public void deleteEvaluation(Long id) {
        if (!evaluationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evaluation non trouvée avec l'ID: " + id);
        }
        evaluationRepository.deleteById(id);
    }

    public List<EtudiantResponse> getEtudiantsByModuleAndSession(Long moduleId, Long sessionId) {
        // Récupérer le module
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + moduleId));

        // Vérifier que la session existe
        sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session non trouvée avec l'ID: " + sessionId));

        // Récupérer les étudiants de la classe associée au module
        List<Etudiant> etudiants = etudiantRepository.findByClasseIdAndActifTrue(module.getClasseId());

        // Convertir et retourner les réponses
        return etudiants.stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }
}
