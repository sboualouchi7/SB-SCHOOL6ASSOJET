package ma.salman.sbschoolassojet.dto.etudiant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EtudiantResponse extends UtilisateurResponse {

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateInscription;
    private String filiere;
    private Long classeId;
    private Long niveauId;
    private String numeroEtudiant;
    private boolean actif;
    private String anneeScolaire;
    private String nomClasse;

}
