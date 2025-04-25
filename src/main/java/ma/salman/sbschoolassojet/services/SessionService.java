package ma.salman.sbschoolassojet.services;
import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.session.SessionRequest;
import ma.salman.sbschoolassojet.dto.session.SessionResponse;
import ma.salman.sbschoolassojet.enums.StatutSession;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.SessionMapper;
import ma.salman.sbschoolassojet.models.Session;
import ma.salman.sbschoolassojet.models.Module;
import ma.salman.sbschoolassojet.models.SessionModule;
import ma.salman.sbschoolassojet.models.Utilisateur;
import ma.salman.sbschoolassojet.repositories.ModuleRepository;
import ma.salman.sbschoolassojet.repositories.SessionModuleRepository;
import ma.salman.sbschoolassojet.repositories.SessionRepository;
import ma.salman.sbschoolassojet.repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ModuleRepository moduleRepository;
    private final SessionModuleRepository sessionModuleRepository;
    private final SessionMapper sessionMapper;

    public List<SessionResponse> getAllSessions() {
        return sessionRepository.findAll().stream()
                .map(sessionMapper::toDto)
                .collect(Collectors.toList());
    }

    public SessionResponse getSessionById(Long id) {
        return sessionRepository.findById(id)
                .map(sessionMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Session non trouvée avec l'ID: " + id));
    }

    public List<SessionResponse> getSessionsByResponsable(Long responsableId) {
        return sessionRepository.findByResponsableId(responsableId).stream()
                .map(sessionMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SessionResponse> getSessionsByAnneeScolaire(String anneeScolaire) {
        return sessionRepository.findByAnneeScolaire(anneeScolaire).stream()
                .map(sessionMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SessionResponse> getSessionsByStatut(StatutSession statut) {
        return sessionRepository.findByStatut(statut).stream()
                .map(sessionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SessionResponse createSession(SessionRequest request) {
        Session session = sessionMapper.toEntity(request);

        // Vérifier que le responsable existe
        Utilisateur responsable = utilisateurRepository.findById(request.getResponsableId())
                .orElseThrow(() -> new ResourceNotFoundException("Responsable non trouvé avec l'ID: " + request.getResponsableId()));

        Session savedSession = sessionRepository.save(session);

        // Ajouter les modules si fournis
        if (request.getModuleIds() != null && !request.getModuleIds().isEmpty()) {
            int ordre = 1;
            for (Long moduleId : request.getModuleIds()) {
                // Vérifier que le module existe
                Module module = moduleRepository.findById(moduleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + moduleId));

                SessionModule sessionModule = SessionModule.builder()
                        .sessionId(savedSession.getId())
                        .moduleId(moduleId)
                        .dateAjout(LocalDate.now())
                        .ordre(ordre++)
                        .build();

                sessionModuleRepository.save(sessionModule);
            }
        }

        return sessionMapper.toDto(savedSession);
    }

    @Transactional
    public SessionResponse updateSession(Long id, SessionRequest request) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session non trouvée avec l'ID: " + id));

        sessionMapper.updateEntityFromDto(request, session);

        // Vérifier que le responsable existe s'il est modifié
        if (request.getResponsableId() != null && !request.getResponsableId().equals(session.getResponsableId())) {
            Utilisateur responsable = utilisateurRepository.findById(request.getResponsableId())
                    .orElseThrow(() -> new ResourceNotFoundException("Responsable non trouvé avec l'ID: " + request.getResponsableId()));
        }

        Session updatedSession = sessionRepository.save(session);

        // Mettre à jour les modules si fournis
        if (request.getModuleIds() != null) {
            // Supprimer les anciens modules de la session
            List<SessionModule> oldSessionModules = sessionModuleRepository.findBySessionId(id);
            sessionModuleRepository.deleteAll(oldSessionModules);

            // Ajouter les nouveaux modules
            int ordre = 1;
            for (Long moduleId : request.getModuleIds()) {
                // Vérifier que le module existe
                Module module = moduleRepository.findById(moduleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + moduleId));

                SessionModule sessionModule = SessionModule.builder()
                        .sessionId(updatedSession.getId())
                        .moduleId(moduleId)
                        .dateAjout(LocalDate.now())
                        .ordre(ordre++)
                        .build();

                sessionModuleRepository.save(sessionModule);
            }
        }

        return sessionMapper.toDto(updatedSession);
    }

    @Transactional
    public SessionResponse updateStatut(Long id, StatutSession statut) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session non trouvée avec l'ID: " + id));

        session.setStatut(statut);
        return sessionMapper.toDto(sessionRepository.save(session));
    }

    @Transactional
    public void deleteSession(Long id) {
        if (!sessionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Session non trouvée avec l'ID: " + id);
        }

        // Supprimer d'abord les modules associés à la session
        List<SessionModule> sessionModules = sessionModuleRepository.findBySessionId(id);
        sessionModuleRepository.deleteAll(sessionModules);

        sessionRepository.deleteById(id);
    }

    @Transactional
    public SessionResponse activerSession(Long id, boolean actif) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session non trouvée avec l'ID: " + id));

        session.setActif(actif);
        return sessionMapper.toDto(sessionRepository.save(session));
    }
}
