package ma.salman.sbschoolassojet.services;

import ma.salman.sbschoolassojet.dto.module.ModuleRequest;
import ma.salman.sbschoolassojet.dto.module.ModuleResponse;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.ModuleMapper;
import ma.salman.sbschoolassojet.models.Classe;
import ma.salman.sbschoolassojet.models.Enseignant;
import ma.salman.sbschoolassojet.models.Module;
import ma.salman.sbschoolassojet.models.Niveau;
import ma.salman.sbschoolassojet.repositories.ClasseRepository;
import ma.salman.sbschoolassojet.repositories.EnseignantRepository;
import ma.salman.sbschoolassojet.repositories.ModuleRepository;
import ma.salman.sbschoolassojet.repositories.NiveauRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}