package ma.salman.sbschoolassojet.dto.etudiant;

import lombok.EqualsAndHashCode;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EtudiantRequest extends UtilisateurRequest {
    @NotNull(message = "La date d'inscription est requise")
    private Date dateInscription;

    private String filiere;

    @NotNull(message = "La classe est requise")
    private Long classeId;

    @NotNull(message = "Le niveau est requis")
    private Long niveauId;

    @NotBlank(message = "Le numéro d'étudiant est requis")
    private String numeroEtudiant;

    @NotBlank(message = "L'année scolaire est requise")
    private String anneeScolaire;
}
