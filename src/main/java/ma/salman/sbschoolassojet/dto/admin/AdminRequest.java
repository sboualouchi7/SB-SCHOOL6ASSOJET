package ma.salman.sbschoolassojet.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurRequest;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AdminRequest extends UtilisateurRequest {
}
