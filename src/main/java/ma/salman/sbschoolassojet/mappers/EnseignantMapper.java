package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.enseignant.EnseignantRequest;
import ma.salman.sbschoolassojet.dto.enseignant.EnseignantResponse;
import ma.salman.sbschoolassojet.models.Enseignant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Base64;

@Mapper(componentModel = "spring",uses = {DateMapper.class})
public interface EnseignantMapper {
  //  EnseignantMapper INSTANCE = Mappers.getMapper(EnseignantMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "actifAccount", constant = "true")
    Enseignant toEntity(EnseignantRequest request);

    EnseignantResponse toDto(Enseignant entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    void updateEntityFromDto(EnseignantRequest request, @MappingTarget Enseignant entity);
    default byte[] stringToByteArray(String value) {
        if (value == null) {
            return null;
        }

        // Gestion des données encodées en Base64 avec préfixe data:image
        if (value.startsWith("data:image/")) {
            int commaIndex = value.indexOf(",");
            if (commaIndex > 0) {
                value = value.substring(commaIndex + 1);
                return Base64.getDecoder().decode(value);
            }
        }

        // Si c'est un nom de fichier (contient une extension), simplement retourner les octets
        if (value.contains(".")) {
            return value.getBytes();
        }

        // Pour les autres cas, essayer la conversion Base64 avec un fallback
        try {
            return Base64.getDecoder().decode(value);
        } catch (IllegalArgumentException e) {
            // Si la conversion Base64 échoue, retourner les octets bruts
            return value.getBytes();
        }
    }
}
