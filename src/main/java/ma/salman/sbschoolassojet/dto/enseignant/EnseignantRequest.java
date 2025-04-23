package ma.salman.sbschoolassojet.dto.enseignant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurRequest;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EnseignantRequest extends UtilisateurRequest {
    @NotBlank(message = "Le numéro de carte est requis")
    private String numeroCarte;

    @NotNull(message = "Le département est requis")
    private Long departementId;

    @NotNull(message = "La date d'embauche est requise")
    private Date dateEmbauche;

    private String specialite;
}
