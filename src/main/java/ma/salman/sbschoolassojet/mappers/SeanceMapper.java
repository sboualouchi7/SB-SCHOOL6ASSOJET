package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.seance.SeanceRequest;
import ma.salman.sbschoolassojet.dto.seance.SeanceResponse;
import ma.salman.sbschoolassojet.models.Seance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.sql.Time;
import java.time.LocalTime;

@Mapper(componentModel = "spring", uses = {DateMapper.class})
public interface SeanceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actif", constant = "true")
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "enseignant", ignore = true)
    @Mapping(target = "absences", ignore = true)
    Seance toEntity(SeanceRequest request);

    @Mapping(target = "libelleModule", source = "module.libelle")
    @Mapping(target = "nomEnseignant", expression = "java(entity.getEnseignant() != null ? entity.getEnseignant().getNom() + ' ' + entity.getEnseignant().getPrenom() : null)")
    SeanceResponse toDto(Seance entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "enseignant", ignore = true)
    @Mapping(target = "absences", ignore = true)
    void updateEntityFromDto(SeanceRequest request, @MappingTarget Seance entity);

    // Méthode de conversion directement dans l'interface
    default Time map(LocalTime localTime) {
        if (localTime == null) {
            return null;
        }
        return Time.valueOf(localTime);
    }

    // Méthode inverse si nécessaire
    default LocalTime map(Time time) {
        if (time == null) {
            return null;
        }
        return time.toLocalTime();
    }
}