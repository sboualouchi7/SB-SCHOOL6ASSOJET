package ma.salman.sbschoolassojet.dto.enseignant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurResponse;

import java.time.LocalDate;
import java.util.Date;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EnseignantResponse extends UtilisateurResponse {
    private String numeroCarte;
    private Long departementId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateEmbauche;
    private String specialite;
}
