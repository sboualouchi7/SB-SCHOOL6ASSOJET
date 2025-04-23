package ma.salman.sbschoolassojet.dto.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.StatusDocument;
import ma.salman.sbschoolassojet.enums.TypeDocument;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {
    private Long id;
    private Long etudiantId;
    private Long demandeurId;
    private Date dateCreation;
    private Date dateDelivrance;
    private String fichierUrl;
    private String commentaire;
    private boolean actif;
    private TypeDocument type;
    private StatusDocument status;
    private String nomEtudiant;
    private String nomDemandeur;
}
