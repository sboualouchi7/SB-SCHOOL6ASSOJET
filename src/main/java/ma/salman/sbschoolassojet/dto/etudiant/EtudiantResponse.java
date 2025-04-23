package ma.salman.sbschoolassojet.dto.etudiant;

import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EtudiantResponse extends UtilisateurResponse {
    private Date dateInscription;
    private String filiere;
    private Long classeId;
    private Long niveauId;
    private String numeroEtudiant;
    private boolean actif;
    private String anneeScolaire;
    private String nomClasse;

}
