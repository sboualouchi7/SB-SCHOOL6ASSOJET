package ma.salman.sbschoolassojet.mappers;
import ma.salman.sbschoolassojet.dto.sessionmodule.SessionModuleRequest;
import ma.salman.sbschoolassojet.dto.sessionmodule.SessionModuleResponse;
import ma.salman.sbschoolassojet.models.SessionModule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface SessionModuleMapper {
    SessionModuleMapper INSTANCE = Mappers.getMapper(SessionModuleMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateAjout", expression = "java(new java.util.Date())")
    @Mapping(target = "session", ignore = true)
    @Mapping(target = "module", ignore = true)
    SessionModule toEntity(SessionModuleRequest request);

    @Mapping(target = "nomSession", source = "session.nom")
    @Mapping(target = "libelleModule", source = "module.libelle")
    SessionModuleResponse toDto(SessionModule entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateAjout", ignore = true)
    @Mapping(target = "session", ignore = true)
    @Mapping(target = "module", ignore = true)
    void updateEntityFromDto(SessionModuleRequest request, @MappingTarget SessionModule entity);
}
