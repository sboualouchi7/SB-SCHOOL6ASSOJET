package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurRequest;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurResponse;
import ma.salman.sbschoolassojet.models.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Base64;

@Mapper(componentModel = "spring",uses = {DateMapper.class})
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

    // Méthode de conversion au cas où vous reviendriez à byte[] dans votre entité
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