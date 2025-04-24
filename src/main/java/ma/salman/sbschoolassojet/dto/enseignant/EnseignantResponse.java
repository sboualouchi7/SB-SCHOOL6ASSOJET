package ma.salman.sbschoolassojet.dto.enseignant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurResponse;

import java.util.Date;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EnseignantResponse extends UtilisateurResponse {
    private String numeroCarte;
    private Long departementId;
    private Date dateEmbauche;
    private String specialite;
}
