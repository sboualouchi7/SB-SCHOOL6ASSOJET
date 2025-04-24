package ma.salman.sbschoolassojet.dto.parent;

import lombok.EqualsAndHashCode;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantResponse;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ParentResponse extends UtilisateurResponse {
    private String professionParent;
    private String relationAvecEtudiant;
    private Set<EtudiantResponse> enfants;

}
