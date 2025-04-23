package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.evaluation.EvaluationRequest;
import ma.salman.sbschoolassojet.dto.evaluation.EvaluationResponse;
import ma.salman.sbschoolassojet.models.Evaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {
    EvaluationMapper INSTANCE = Mappers.getMapper(EvaluationMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estValidee", constant = "false")
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "enseignant", ignore = true)
    @Mapping(target = "session", ignore = true)
    Evaluation toEntity(EvaluationRequest request);

    @Mapping(target = "nomEtudiant", expression = "java(entity.getEtudiant() != null ? entity.getEtudiant().getNom() + ' ' + entity.getEtudiant().getPrenom() : null)")
    @Mapping(target = "libelleModule", source = "module.libelle")
    @Mapping(target = "nomEnseignant", expression = "java(entity.getEnseignant() != null ? entity.getEnseignant().getNom() + ' ' + entity.getEnseignant().getPrenom() : null)")
    @Mapping(target = "nomSession", source = "session.nom")
    EvaluationResponse toDto(Evaluation entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estValidee", ignore = true)
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "enseignant", ignore = true)
    @Mapping(target = "session", ignore = true)
    void updateEntityFromDto(EvaluationRequest request, @MappingTarget Evaluation entity);
}
