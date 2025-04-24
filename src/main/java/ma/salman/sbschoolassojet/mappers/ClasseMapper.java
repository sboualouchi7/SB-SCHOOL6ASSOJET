package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.classe.ClasseRequest;
import ma.salman.sbschoolassojet.dto.classe.ClasseResponse;
import ma.salman.sbschoolassojet.models.Classe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClasseMapper {
   // ClasseMapper INSTANCE = Mappers.getMapper(ClasseMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "actif", constant = "true")
    @Mapping(target = "niveau", ignore = true)
    @Mapping(target = "etudiants", ignore = true)
    @Mapping(target = "modules", ignore = true)
    Classe toEntity(ClasseRequest request);

    @Mapping(target = "labelNiveau", source = "niveau.label")
    ClasseResponse toDto(Classe entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "niveau", ignore = true)
    @Mapping(target = "etudiants", ignore = true)
    @Mapping(target = "modules", ignore = true)
    void updateEntityFromDto(ClasseRequest request, @MappingTarget Classe entity);
}
