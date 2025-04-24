package ma.salman.sbschoolassojet.dto.parent;

import lombok.EqualsAndHashCode;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ParentRequest extends UtilisateurRequest {
    @NotBlank(message = "La relation avec l'étudiant est requise")
    private String relationAvecEtudiant;

    private List<Long> enfantsIds;

}
