package ma.salman.sbschoolassojet.dto.utilisateur;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.enums.Sexe;

import java.time.LocalDate;
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
    @JsonFormat(pattern = "dd-MM-yyyy")

    private LocalDate dateNaissance;
    private String adresse;
    private String username;
    private byte[] photo;
    private boolean actifAccount;
    private Role role;
    private Sexe sexe;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateCreation;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateModification;
}
