package ma.salman.sbschoolassojet.mappers;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurRequest;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurResponse;
import ma.salman.sbschoolassojet.models.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
   // UtilisateurMapper INSTANCE = Mappers.getMapper(UtilisateurMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "actifAccount", constant = "true")
    Utilisateur toEntity(UtilisateurRequest request);

    UtilisateurResponse toDto(Utilisateur entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    void updateEntityFromDto(UtilisateurRequest request, @MappingTarget Utilisateur entity);
}
