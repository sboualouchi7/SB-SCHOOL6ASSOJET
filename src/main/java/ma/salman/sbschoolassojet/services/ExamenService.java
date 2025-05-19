package ma.salman.sbschoolassojet.services;


import ma.salman.sbschoolassojet.dto.Examen.ExamenRequestDTO;
import ma.salman.sbschoolassojet.dto.Examen.ExamenResponseDTO;
import ma.salman.sbschoolassojet.enums.TypeExamen;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.ExamenMapper;
import ma.salman.sbschoolassojet.models.Classe;
import ma.salman.sbschoolassojet.models.Examen;
import ma.salman.sbschoolassojet.repositories.ClasseRepository;
import ma.salman.sbschoolassojet.repositories.ExamenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamenService {

    private final ExamenRepository examenRepository;
    private final ClasseRepository classeRepository;
    private final ExamenMapper examenMapper;

    @Transactional(readOnly = true)
    public List<ExamenResponseDTO> getAllExamens() {
        return examenRepository.findAll().stream()
                .map(examenMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExamenResponseDTO getExamenById(Integer id) {
        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examen non trouvé avec l'ID: " + id));
        return examenMapper.toDto(examen);
    }

    @Transactional(readOnly = true)
    public List<ExamenResponseDTO> getExamensByClasseId(Long classeId) {
        return examenRepository.findByClasseId(classeId).stream()
                .map(examenMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExamenResponseDTO> getExamensByType(TypeExamen typeExamen) {
        return examenRepository.findByTypeExamen(typeExamen).stream()
                .map(examenMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExamenResponseDTO> getExamensByDateRange(LocalDate debut, LocalDate fin) {
        return examenRepository.findByDateExamenBetween(debut, fin).stream()
                .map(examenMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExamenResponseDTO createExamen(ExamenRequestDTO examenDTO) {
        // Vérifier si la classe existe
        Classe classe = classeRepository.findById(examenDTO.getClasseId())
                .orElseThrow(() -> new ResourceNotFoundException("Classe non trouvée avec l'ID: " + examenDTO.getClasseId()));

        Examen examen = examenMapper.toEntity(examenDTO);
        examen.setClasse(classe);

        Examen savedExamen = examenRepository.save(examen);
        return examenMapper.toDto(savedExamen);
    }

    @Transactional
    public ExamenResponseDTO updateExamen(Integer id, ExamenRequestDTO examenDTO) {
        Examen existingExamen = examenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examen non trouvé avec l'ID: " + id));

        // Vérifier si la classe a changé et si la nouvelle classe existe
        if (!existingExamen.getClasse().getId().equals(examenDTO.getClasseId())) {
            Classe classe = classeRepository.findById(examenDTO.getClasseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Classe non trouvée avec l'ID: " + examenDTO.getClasseId()));
            existingExamen.setClasse(classe);
        }

        examenMapper.updateEntityFromDto(examenDTO, existingExamen);
        Examen updatedExamen = examenRepository.save(existingExamen);
        return examenMapper.toDto(updatedExamen);
    }

    @Transactional
    public void deleteExamen(Integer id) {
        if (!examenRepository.existsById(id)) {
            throw new ResourceNotFoundException("Examen non trouvé avec l'ID: " + id);
        }
        examenRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean isExamenExistsForClasseAndType(Long classeId, TypeExamen typeExamen) {
        return examenRepository.existsByClasseIdAndTypeExamen(classeId, typeExamen);
    }

    // Méthode pour associer un module à un examen (à utiliser dans un contrôleur de Module)
    @Transactional
    public void associateModuleToExamen(Long moduleId, Integer examenId) {
        // Cette méthode devrait être implémentée dans le service de Module
        // car c'est le Module qui a la relation avec Examen (examen_id)
    }
}