package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.parent.ParentRequest;
import ma.salman.sbschoolassojet.dto.parent.ParentResponse;
import ma.salman.sbschoolassojet.models.Parent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Base64;

@Mapper(componentModel = "spring",uses = {DateMapper.class})
public interface ParentMapper {
   // ParentMapper INSTANCE = Mappers.getMapper(ParentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "actifAccount", constant = "true")
    @Mapping(target = "enfants", expression = "java(new java.util.HashSet<>())")
    Parent toEntity(ParentRequest request);

    ParentResponse toDto(Parent entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "enfants", expression = "java(new java.util.HashSet<>())")
    void updateEntityFromDto(ParentRequest request, @MappingTarget Parent entity);
    default byte[] stringToByteArray(String value) {
        if (value == null) {
            return null;
        }
        // Gestion des données encodées en Base64 avec préfixe data:image
        if (value.startsWith("data:image/")) {
            int commaIndex = value.indexOf(",");
            if (commaIndex > 0) {
                value = value.substring(commaIndex + 1);
            }
        }
        return Base64.getDecoder().decode(value);
    }
}
