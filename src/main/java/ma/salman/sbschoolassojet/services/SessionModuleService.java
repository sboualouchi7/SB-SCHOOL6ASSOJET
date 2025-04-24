package ma.salman.sbschoolassojet.services;

import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.sessionmodule.SessionModuleRequest;
import ma.salman.sbschoolassojet.dto.sessionmodule.SessionModuleResponse;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.SessionModuleMapper;
import ma.salman.sbschoolassojet.models.Session;
import ma.salman.sbschoolassojet.models.Module;
import ma.salman.sbschoolassojet.models.SessionModule;
import ma.salman.sbschoolassojet.repositories.ModuleRepository;
import ma.salman.sbschoolassojet.repositories.SessionModuleRepository;
import ma.salman.sbschoolassojet.repositories.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionModuleService {

    private final SessionModuleRepository sessionModuleRepository;
    private final SessionRepository sessionRepository;
    private final ModuleRepository moduleRepository;
    private final SessionModuleMapper sessionModuleMapper;

    public List<SessionModuleResponse> getAllSessionModules() {
        return sessionModuleRepository.findAll().stream()
                .map(sessionModuleMapper::toDto)
                .collect(Collectors.toList());
    }

    public SessionModuleResponse getSessionModuleById(Long id) {
        return sessionModuleRepository.findById(id)
                .map(sessionModuleMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("SessionModule non trouvé avec l'ID: " + id));
    }

    public List<SessionModuleResponse> getSessionModulesBySession(Long sessionId) {
        return sessionModuleRepository.findBySessionId(sessionId).stream()
                .map(sessionModuleMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SessionModuleResponse> getSessionModulesByModule(Long moduleId) {
        return sessionModuleRepository.findByModuleId(moduleId).stream()
                .map(sessionModuleMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SessionModuleResponse> getSessionModulesBySessionOrdered(Long sessionId) {
        return sessionModuleRepository.findBySessionIdOrderByOrdreAsc(sessionId).stream()
                .map(sessionModuleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SessionModuleResponse createSessionModule(SessionModuleRequest request) {
        // Vérifier que la session existe
        Session session = sessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Session non trouvée avec l'ID: " + request.getSessionId()));

        // Vérifier que le module existe
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + request.getModuleId()));

        SessionModule sessionModule = sessionModuleMapper.toEntity(request);
        return sessionModuleMapper.toDto(sessionModuleRepository.save(sessionModule));
    }

    @Transactional
    public SessionModuleResponse updateSessionModule(Long id, SessionModuleRequest request) {
        SessionModule sessionModule = sessionModuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SessionModule non trouvé avec l'ID: " + id));

        // Vérifier que la session existe si elle est modifiée
        if (request.getSessionId() != null && !request.getSessionId().equals(sessionModule.getSessionId())) {
            Session session = sessionRepository.findById(request.getSessionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Session non trouvée avec l'ID: " + request.getSessionId()));
        }

        // Vérifier que le module existe s'il est modifié
        if (request.getModuleId() != null && !request.getModuleId().equals(sessionModule.getModuleId())) {
            Module module = moduleRepository.findById(request.getModuleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + request.getModuleId()));
        }

        sessionModuleMapper.updateEntityFromDto(request, sessionModule);
        return sessionModuleMapper.toDto(sessionModuleRepository.save(sessionModule));
    }

    @Transactional
    public void deleteSessionModule(Long id) {
        if (!sessionModuleRepository.existsById(id)) {
            throw new ResourceNotFoundException("SessionModule non trouvé avec l'ID: " + id);
        }
        sessionModuleRepository.deleteById(id);
    }
}
