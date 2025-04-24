package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.module.ModuleRequest;
import ma.salman.sbschoolassojet.dto.module.ModuleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ma.salman.sbschoolassojet.models.Module;

@Mapper(componentModel = "spring")
public interface ModuleMapper {
   // ModuleMapper INSTANCE = Mappers.getMapper(ModuleMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "actif", constant = "true")
    @Mapping(target = "classe", ignore = true)
    @Mapping(target = "enseignant", ignore = true)
    @Mapping(target = "seances", ignore = true)
    @Mapping(target = "evaluations", ignore = true)
    @Mapping(target = "sessionModules", ignore = true)
    Module toEntity(ModuleRequest request);

    @Mapping(target = "nomEnseignant", expression = "java(entity.getEnseignant() != null ? entity.getEnseignant().getNom() + ' ' + entity.getEnseignant().getPrenom() : null)")
    @Mapping(target = "nomClasse", source = "classe.nom")
    ModuleResponse toDto(Module entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "classe", ignore = true)
    @Mapping(target = "enseignant", ignore = true)
    @Mapping(target = "seances", ignore = true)
    @Mapping(target = "evaluations", ignore = true)
    @Mapping(target = "sessionModules", ignore = true)
    void updateEntityFromDto(ModuleRequest request, @MappingTarget Module entity);


}
