package ma.salman.sbschoolassojet.dto.utilisateur;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.enums.Sexe;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurRequest {
    @NotBlank(message = "Le nom est requis")
    private String nom;

    @NotBlank(message = "Le prénom est requis")
    private String prenom;

    @NotBlank(message = "L'email est requis")
    @Email(message = "Format d'email invalide")
    private String email;

    private String telephone;

    @JsonFormat(pattern = "dd-MM-yyyy")

    private LocalDate dateNaissance;
    private String adresse;

    @NotBlank(message = "Le nom d'utilisateur est requis")
    private String username;

    @NotBlank(message = "Le mot de passe est requis")
    private String password;

    private String photo;

   // @NotNull(message = "Le rôle est requis")
    private Role role;

    @NotNull(message = "Le sexe est requis")
    private Sexe sexe;
}
