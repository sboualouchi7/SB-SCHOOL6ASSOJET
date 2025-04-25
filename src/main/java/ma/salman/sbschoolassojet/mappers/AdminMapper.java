package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.admin.AdminRequest;
import ma.salman.sbschoolassojet.dto.admin.AdminResponse;
import ma.salman.sbschoolassojet.models.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Base64;

@Mapper(componentModel = "spring",uses = {DateMapper.class})
public interface AdminMapper {
    //AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "actifAccount", constant = "true")
    Admin toEntity(AdminRequest request);

    AdminResponse toDto(Admin entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    void updateEntityFromDto(AdminRequest request, @MappingTarget Admin entity);
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
