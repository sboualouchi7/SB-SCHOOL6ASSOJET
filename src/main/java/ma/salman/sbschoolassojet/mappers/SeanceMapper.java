package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.seance.SeanceRequest;
import ma.salman.sbschoolassojet.dto.seance.SeanceResponse;
import ma.salman.sbschoolassojet.models.Seance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SeanceMapper {
    SeanceMapper INSTANCE = Mappers.getMapper(SeanceMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actif", constant = "true")
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "enseignant", ignore = true)
    @Mapping(target = "absences", ignore = true)
    Seance toEntity(SeanceRequest request);

    @Mapping(target = "libelleModule", source = "module.libelle")
    @Mapping(target = "nomEnseignant", expression = "java(entity.getEnseignant() != null ? entity.getEnseignant().getNom() + ' ' + entity.getEnseignant().getPrenom() : null)")
    SeanceResponse toDto(Seance entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "enseignant", ignore = true)
    @Mapping(target = "absences", ignore = true)
    void updateEntityFromDto(SeanceRequest request, @MappingTarget Seance entity);
}
