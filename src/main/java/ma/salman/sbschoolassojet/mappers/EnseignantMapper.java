package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.enseignant.EnseignantRequest;
import ma.salman.sbschoolassojet.dto.enseignant.EnseignantResponse;
import ma.salman.sbschoolassojet.models.Enseignant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EnseignantMapper {
  //  EnseignantMapper INSTANCE = Mappers.getMapper(EnseignantMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "actifAccount", constant = "true")
    Enseignant toEntity(EnseignantRequest request);

    EnseignantResponse toDto(Enseignant entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    void updateEntityFromDto(EnseignantRequest request, @MappingTarget Enseignant entity);
}
