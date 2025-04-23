package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.admin.AdminRequest;
import ma.salman.sbschoolassojet.dto.admin.AdminResponse;
import ma.salman.sbschoolassojet.models.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

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
}
