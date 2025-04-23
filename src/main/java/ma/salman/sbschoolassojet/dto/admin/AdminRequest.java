package ma.salman.sbschoolassojet.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurRequest;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest extends UtilisateurRequest {
}
