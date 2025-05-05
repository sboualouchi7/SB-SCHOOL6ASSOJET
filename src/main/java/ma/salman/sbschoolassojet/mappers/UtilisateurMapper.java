package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurRequest;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurResponse;
import ma.salman.sbschoolassojet.models.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Base64;

@Mapper(componentModel = "spring",uses = {DateMapper.class})
public interface UtilisateurMapper {
    // UtilisateurMapper INSTANCE = Mappers.getMapper(UtilisateurMapper.class);
    Logger logger = LoggerFactory.getLogger(UtilisateurMapper.class);

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
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            // Prétraitement de la chaîne
            if (value.startsWith("data:image/")) {
                int commaIndex = value.indexOf(",");
                if (commaIndex > 0) {
                    value = value.substring(commaIndex + 1);
                }
            }

            // Nettoyer la chaîne des caractères non Base64
            value = value.replaceAll("[^A-Za-z0-9+/=]", "");

            // Vérifier la longueur et la validité avant le décodage
            if (isValidBase64(value)) {
                return Base64.getDecoder().decode(value);
            } else {
                logger.warn("Chaîne Base64 invalide : {}", value);
                return null;
            }
        } catch (Exception e) {
            logger.error("Erreur lors du décodage Base64", e);
            return null;
        }
    }

    private boolean isValidBase64(String s) {
        // Une chaîne Base64 valide doit :
        // - Avoir une longueur multiple de 4
        // - Contenir uniquement des caractères Base64
        return s != null &&
                s.length() % 4 == 0 &&
                s.matches("^[A-Za-z0-9+/]+={0,2}$");
    }
}