package ma.salman.sbschoolassojet.dto.enseignant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurResponse;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EnseignantResponse extends UtilisateurResponse {
    private String numeroCarte;
    private Long departementId;
    private Date dateEmbauche;
    private String specialite;
}
