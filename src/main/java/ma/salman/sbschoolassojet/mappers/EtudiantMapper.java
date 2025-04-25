package ma.salman.sbschoolassojet.mappers;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantRequest;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantResponse;
import ma.salman.sbschoolassojet.models.Etudiant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Base64;


@Mapper(componentModel = "spring",uses = {DateMapper.class})
public interface EtudiantMapper {
    //EtudiantMapper INSTANCE = Mappers.getMapper(EtudiantMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "actifAccount", constant = "true")
    @Mapping(target = "actif", constant = "true")
    @Mapping(target = "classe", ignore = true)
    @Mapping(target = "evaluations", ignore = true)
    @Mapping(target = "absences", ignore = true)
    @Mapping(target = "documents", ignore = true)
    @Mapping(target = "parents", ignore = true)
    Etudiant toEntity(EtudiantRequest request);

    @Mapping(target = "nomClasse", source = "classe.nom")
    EtudiantResponse toDto(Etudiant entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "classe", ignore = true)
    @Mapping(target = "evaluations", ignore = true)
    @Mapping(target = "absences", ignore = true)
    @Mapping(target = "documents", ignore = true)
    @Mapping(target = "parents", ignore = true)
    void updateEntityFromDto(EtudiantRequest request, @MappingTarget Etudiant entity);

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

