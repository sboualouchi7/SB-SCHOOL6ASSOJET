package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.absence.AbsenceRequest;
import ma.salman.sbschoolassojet.dto.absence.AbsenceResponse;
import ma.salman.sbschoolassojet.models.Absence;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AbsenceMapper {
    AbsenceMapper INSTANCE = Mappers.getMapper(AbsenceMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "validee", constant = "false")
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "seance", ignore = true)
    Absence toEntity(AbsenceRequest request);

    @Mapping(target = "nomEtudiant", expression = "java(entity.getEtudiant() != null ? entity.getEtudiant().getNom() + ' ' + entity.getEtudiant().getPrenom() : null)")
    @Mapping(target = "moduleSeance", expression = "java(entity.getSeance() != null && entity.getSeance().getModule() != null ? entity.getSeance().getModule().getLibelle() : null)")
    AbsenceResponse toDto(Absence entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "validee", ignore = true)
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "seance", ignore = true)
    void updateEntityFromDto(AbsenceRequest request, @MappingTarget Absence entity);
}
