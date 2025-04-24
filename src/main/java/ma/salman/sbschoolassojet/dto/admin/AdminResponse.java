package ma.salman.sbschoolassojet.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurResponse;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder

public class AdminResponse extends UtilisateurResponse {

}
