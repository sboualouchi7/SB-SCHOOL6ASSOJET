package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.session.SessionRequest;
import ma.salman.sbschoolassojet.dto.session.SessionResponse;
import ma.salman.sbschoolassojet.models.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actif", constant = "true")
    @Mapping(target = "evaluations", ignore = true)
    @Mapping(target = "sessionModules", ignore = true)
    @Mapping(target = "responsable", ignore = true)
    Session toEntity(SessionRequest request);

    @Mapping(target = "nomResponsable", expression = "java(entity.getResponsable() != null ? entity.getResponsable().getNom() + ' ' + entity.getResponsable().getPrenom() : null)")
    SessionResponse toDto(Session entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "evaluations", ignore = true)
    @Mapping(target = "sessionModules", ignore = true)
    @Mapping(target = "responsable", ignore = true)
    void updateEntityFromDto(SessionRequest request, @MappingTarget Session entity);
}
