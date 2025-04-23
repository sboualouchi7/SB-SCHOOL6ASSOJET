package ma.salman.sbschoolassojet.dto.utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.enums.Sexe;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String dateNaissance;
    private String adresse;
    private String username;
    private byte[] photo;
    private boolean actifAccount;
    private Role role;
    private Sexe sexe;
    private Date dateCreation;
    private Date dateModification;
}
