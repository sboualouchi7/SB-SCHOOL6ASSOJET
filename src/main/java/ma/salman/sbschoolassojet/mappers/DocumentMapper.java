package ma.salman.sbschoolassojet.mappers;

import ma.salman.sbschoolassojet.dto.document.DocumentRequest;
import ma.salman.sbschoolassojet.dto.document.DocumentResponse;
import ma.salman.sbschoolassojet.models.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
   // DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", expression = "java(new java.util.Date())")
    @Mapping(target = "dateDelivrance", ignore = true)
    @Mapping(target = "fichierUrl", ignore = true)
    @Mapping(target = "actif", constant = "true")
    @Mapping(target = "status", constant = "DEMANDE")//j'ai chané StatusDocument.DEMANDE a demande
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "demandeur", ignore = true)
    Document toEntity(DocumentRequest request);

    @Mapping(target = "nomEtudiant", expression = "java(entity.getEtudiant() != null ? entity.getEtudiant().getNom() + ' ' + entity.getEtudiant().getPrenom() : null)")
    @Mapping(target = "nomDemandeur", expression = "java(entity.getDemandeur() != null ? entity.getDemandeur().getNom() + ' ' + entity.getDemandeur().getPrenom() : null)")
    DocumentResponse toDto(Document entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateDelivrance", ignore = true)
    @Mapping(target = "fichierUrl", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "demandeur", ignore = true)
    void updateEntityFromDto(DocumentRequest request, @MappingTarget Document entity);
}
