package ma.salman.sbschoolassojet.services;

import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.classe.ClasseRequest;
import ma.salman.sbschoolassojet.dto.classe.ClasseResponse;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.ClasseMapper;
import ma.salman.sbschoolassojet.models.Classe;
import ma.salman.sbschoolassojet.models.Niveau;
import ma.salman.sbschoolassojet.repositories.ClasseRepository;
import ma.salman.sbschoolassojet.repositories.NiveauRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClasseService {
    private final ClasseRepository classeRepository;
    private final NiveauRepository niveauRepository;
    private final ClasseMapper classeMapper;

    public List<ClasseResponse> getAllClasses() {
        return classeRepository.findAll().stream()
                .map(classeMapper::toDto)
                .collect(Collectors.toList());
    }

    public ClasseResponse getClasseById(Long id) {
        return classeRepository.findById(id)
                .map(classeMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Classe non trouvée avec l'ID: " + id));
    }
    /**
     * Récupère toutes les classes associées à un enseignant
     *
     * @param enseignantId ID de l'enseignant
     * @return Liste des classes de l'enseignant
     */
    public List<ClasseResponse> getClassesByEnseignant(Long enseignantId) {
        // Récupérer les classes où l'enseignant donne des cours
        List<Classe> classes = classeRepository.findByEnseignantId(enseignantId);

        // Convertir en DTOs avec le mapper
        return classes.stream()
                .map(classeMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<ClasseResponse> getClassesByNiveau(Long niveauId) {
        return classeRepository.findByNiveauId(niveauId).stream()
                .map(classeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ClasseResponse> getClassesByAnneeScolaire(String anneeScolaire) {
        return classeRepository.findByAnneeScolaire(anneeScolaire).stream()
                .map(classeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClasseResponse createClasse(ClasseRequest request) {
        Classe classe = classeMapper.toEntity(request);

        // Vérifier que le niveau existe
        Niveau niveau = niveauRepository.findById(request.getNiveauId())
                .orElseThrow(() -> new ResourceNotFoundException("Niveau non trouvé avec l'ID: " + request.getNiveauId()));

        return classeMapper.toDto(classeRepository.save(classe));
    }

    @Transactional
    public ClasseResponse updateClasse(Long id, ClasseRequest request) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classe non trouvée avec l'ID: " + id));

        classeMapper.updateEntityFromDto(request, classe);

        // Vérifier que le niveau existe s'il est modifié
        if (request.getNiveauId() != null && !request.getNiveauId().equals(classe.getNiveauId())) {
            Niveau niveau = niveauRepository.findById(request.getNiveauId())
                    .orElseThrow(() -> new ResourceNotFoundException("Niveau non trouvé avec l'ID: " + request.getNiveauId()));
        }

        return classeMapper.toDto(classeRepository.save(classe));
    }

    @Transactional
    public void deleteClasse(Long id) {
        if (!classeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Classe non trouvée avec l'ID: " + id);
        }
        classeRepository.deleteById(id);
    }

    @Transactional
    public ClasseResponse activerClasse(Long id, boolean actif) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classe non trouvée avec l'ID: " + id));

        classe.setActif(actif);
        return classeMapper.toDto(classeRepository.save(classe));
    }
}
