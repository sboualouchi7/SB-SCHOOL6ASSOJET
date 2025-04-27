package ma.salman.sbschoolassojet.services;

import ma.salman.sbschoolassojet.dto.classe.ClasseResponse;
import ma.salman.sbschoolassojet.dto.module.ModuleRequest;
import ma.salman.sbschoolassojet.dto.module.ModuleResponse;
import ma.salman.sbschoolassojet.dto.session.SessionResponse;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.ClasseMapper;
import ma.salman.sbschoolassojet.mappers.ModuleMapper;
import ma.salman.sbschoolassojet.mappers.SessionMapper;
import ma.salman.sbschoolassojet.models.*;
import ma.salman.sbschoolassojet.models.Module;
import ma.salman.sbschoolassojet.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ClasseRepository classeRepository;
    private final NiveauRepository niveauRepository;
    private final EnseignantRepository enseignantRepository;
    private final ModuleMapper moduleMapper;
    private final ClasseMapper classeMapper;
    private final SessionRepository sessionRepository;
    private final SessionModuleRepository sessionModuleRepository;
    private final SessionMapper sessionMapper;

    public List<ModuleResponse> getAllModules() {
        return moduleRepository.findAll().stream()
                .map(moduleMapper::toDto)
                .collect(Collectors.toList());
    }

    public ModuleResponse getModuleById(Long id) {
        return moduleRepository.findById(id)
                .map(moduleMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + id));
    }

    public List<ModuleResponse> getModulesByClasse(Long classeId) {
        return moduleRepository.findByClasseId(classeId).stream()
                .map(moduleMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ModuleResponse> getModulesByEnseignant(Long enseignantId) {
        return moduleRepository.findByEnseignantId(enseignantId).stream()
                .map(moduleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ModuleResponse createModule(ModuleRequest request) {
        Module module = moduleMapper.toEntity(request);

        // Vérifier que la classe existe
        Classe classe = classeRepository.findById(request.getClasseId())
                .orElseThrow(() -> new ResourceNotFoundException("Classe non trouvée avec l'ID: " + request.getClasseId()));

        // Vérifier que le niveau existe
        Niveau niveau = niveauRepository.findById(request.getNiveauId())
                .orElseThrow(() -> new ResourceNotFoundException("Niveau non trouvé avec l'ID: " + request.getNiveauId()));

        // Vérifier que l'enseignant existe
        Enseignant enseignant = enseignantRepository.findById(request.getEnseignantId())
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant non trouvé avec l'ID: " + request.getEnseignantId()));

        return moduleMapper.toDto(moduleRepository.save(module));
    }

    @Transactional
    public ModuleResponse updateModule(Long id, ModuleRequest request) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + id));

        moduleMapper.updateEntityFromDto(request, module);

        // Vérifier que la classe existe si elle est modifiée
        if (request.getClasseId() != null && !request.getClasseId().equals(module.getClasseId())) {
            Classe classe = classeRepository.findById(request.getClasseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Classe non trouvée avec l'ID: " + request.getClasseId()));
        }

        // Vérifier que le niveau existe s'il est modifié
        if (request.getNiveauId() != null && !request.getNiveauId().equals(module.getNiveauId())) {
            Niveau niveau = niveauRepository.findById(request.getNiveauId())
                    .orElseThrow(() -> new ResourceNotFoundException("Niveau non trouvé avec l'ID: " + request.getNiveauId()));
        }

        // Vérifier que l'enseignant existe s'il est modifié
        if (request.getEnseignantId() != null && !request.getEnseignantId().equals(module.getEnseignantId())) {
            Enseignant enseignant = enseignantRepository.findById(request.getEnseignantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Enseignant non trouvé avec l'ID: " + request.getEnseignantId()));
        }

        return moduleMapper.toDto(moduleRepository.save(module));
    }

    @Transactional
    public void deleteModule(Long id) {
        if (!moduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Module non trouvé avec l'ID: " + id);
        }
        moduleRepository.deleteById(id);
    }

    @Transactional
    public ModuleResponse activerModule(Long id, boolean actif) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + id));

        module.setActif(actif);
        return moduleMapper.toDto(moduleRepository.save(module));
    }

    public List<ModuleResponse> getModulesBySessionAndEnseignant(Long sessionId, Long enseignantId) {
        // Vérifier que la session existe
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session non trouvée avec l'ID: " + sessionId));

        // Récupérer tous les modules associés à cette session et enseignés par cet enseignant
        List<Module> modules = new ArrayList<>();

        // 1. Récupérer tous les modules de la session
        List<SessionModule> sessionModules = sessionModuleRepository.findBySessionId(sessionId);

        // 2. Filtrer pour ne garder que ceux enseignés par l'enseignant
        for (SessionModule sessionModule : sessionModules) {
            Module module = moduleRepository.findById(sessionModule.getModuleId()).orElse(null);
            if (module != null && module.getEnseignantId().equals(enseignantId) && module.isActif()) {
                modules.add(module);
            }
        }

        // Convertir et retourner les modules
        return modules.stream()
                .map(moduleMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ClasseResponse> getClassesByModuleAndEnseignant(Long moduleId, Long enseignantId) {
        // Vérifier que le module existe et est enseigné par cet enseignant
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + moduleId));

        if (!module.getEnseignantId().equals(enseignantId)) {
            throw new AccessDeniedException("Vous n'êtes pas autorisé à accéder à ce module");
        }

        // Si le module est associé à une classe spécifique
        if (module.getClasseId() != null) {
            Classe classe = classeRepository.findById(module.getClasseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Classe non trouvée avec l'ID: " + module.getClasseId()));

            return List.of(classeMapper.toDto(classe));
        }

        // Si le module est associé à un niveau (plusieurs classes possibles)
        if (module.getNiveauId() != null) {
            List<Classe> classes = classeRepository.findByNiveauIdAndActifTrue(module.getNiveauId());

            return classes.stream()
                    .map(classeMapper::toDto)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    public List<SessionResponse> getSessionsByEnseignant(Long enseignantId) {
        // Vérifier que l'enseignant existe
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant non trouvé avec l'ID: " + enseignantId));

        // Trouver tous les modules enseignés par cet enseignant
        List<Module> enseignantModules = moduleRepository.findByEnseignantIdAndActifTrue(enseignantId);

        // Récupérer les IDs de ces modules
        List<Long> moduleIds = enseignantModules.stream()
                .map(Module::getId)
                .collect(Collectors.toList());

        // Si aucun module, retourner une liste vide
        if (moduleIds.isEmpty()) {
            return Collections.emptyList();
        }

        // Trouver toutes les sessions contenant ces modules
        List<Long> sessionIds = sessionModuleRepository.findByModuleIdIn(moduleIds).stream()
                .map(SessionModule::getSessionId)
                .distinct()
                .collect(Collectors.toList());

        // Si aucune session, retourner une liste vide
        if (sessionIds.isEmpty()) {
            return Collections.emptyList();
        }

        // Récupérer les sessions complètes
        List<Session> sessions = sessionRepository.findAllById(sessionIds);

        // Ne conserver que les sessions actives
        sessions = sessions.stream()
                .filter(Session::isActif)
                .collect(Collectors.toList());

        // Convertir et retourner les sessions
        return sessions.stream()
                .map(sessionMapper::toDto)
                .collect(Collectors.toList());
    }
}