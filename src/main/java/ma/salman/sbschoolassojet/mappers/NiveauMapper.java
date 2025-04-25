package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.niveau.NiveauRequest;
import ma.salman.sbschoolassojet.dto.niveau.NiveauResponse;
import ma.salman.sbschoolassojet.models.Niveau;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",uses = {DateMapper.class})
public interface NiveauMapper {
  //  NiveauMapper INSTANCE = Mappers.getMapper(NiveauMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actif", constant = "true")
    @Mapping(target = "classes", ignore = true)
    Niveau toEntity(NiveauRequest request);

    NiveauResponse toDto(Niveau entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "classes", ignore = true)
    void updateEntityFromDto(NiveauRequest request, @MappingTarget Niveau entity);
}
