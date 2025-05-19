package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.Examen.ExamenRequestDTO;
import ma.salman.sbschoolassojet.dto.Examen.ExamenResponseDTO;
import ma.salman.sbschoolassojet.models.Examen;
import ma.salman.sbschoolassojet.models.Module;
import ma.salman.sbschoolassojet.models.Classe;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExamenMapper {

    public Examen toEntity(ExamenRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Examen examen = new Examen();
        examen.setDateExamen(dto.getDateExamen());
        examen.setTypeExamen(dto.getTypeExamen());

        // La classe sera définie dans le service
        Classe classe = new Classe();
        classe.setId(dto.getClasseId());
        examen.setClasse(classe);

        return examen;
    }

    public ExamenResponseDTO toDto(Examen examen) {
        if (examen == null) {
            return null;
        }

        List<ExamenResponseDTO.ModuleSimpleDTO> modulesDtos = null;
        if (examen.getModules() != null) {
            modulesDtos = examen.getModules().stream()
                    .map(this::toModuleSimpleDto)
                    .collect(Collectors.toList());
        }

        return ExamenResponseDTO.builder()
                .id(examen.getId())
                .dateExamen(examen.getDateExamen())
                .typeExamen(examen.getTypeExamen())
                .classeId(examen.getClasse() != null ? examen.getClasse().getId() : null)
                .classeNom(examen.getClasse() != null ? examen.getClasse().getNom() : null)
                .modules(modulesDtos)
                .build();
    }

    private ExamenResponseDTO.ModuleSimpleDTO toModuleSimpleDto(Module module) {
        return ExamenResponseDTO.ModuleSimpleDTO.builder()
                .id(module.getId())
                .libelle(module.getLibelle())
                .build();
    }

    public void updateEntityFromDto(ExamenRequestDTO dto, Examen examen) {
        if (dto == null || examen == null) {
            return;
        }

        examen.setDateExamen(dto.getDateExamen());
        examen.setTypeExamen(dto.getTypeExamen());

        // La mise à jour de la classe sera gérée dans le service si nécessaire
    }
}