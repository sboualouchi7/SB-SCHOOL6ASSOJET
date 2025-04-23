package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.parent.ParentRequest;
import ma.salman.sbschoolassojet.dto.parent.ParentResponse;
import ma.salman.sbschoolassojet.models.Parent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EtudiantMapper.class})
public interface ParentMapper {
    ParentMapper INSTANCE = Mappers.getMapper(ParentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "actifAccount", constant = "true")
    @Mapping(target = "enfants", ignore = true)
    Parent toEntity(ParentRequest request);

    ParentResponse toDto(Parent entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "enfants", ignore = true)
    void updateEntityFromDto(ParentRequest request, @MappingTarget Parent entity);
}
