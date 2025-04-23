package ma.salman.sbschoolassojet.mappers;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantRequest;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantResponse;
import ma.salman.sbschoolassojet.models.Etudiant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface EtudiantMapper {
    EtudiantMapper INSTANCE = Mappers.getMapper(EtudiantMapper.class);

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
}

