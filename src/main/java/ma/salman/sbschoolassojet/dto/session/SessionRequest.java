package ma.salman.sbschoolassojet.dto.session;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.StatutSession;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequest {
    @NotBlank(message = "Le nom est requis")
    private String nom;

    @NotNull(message = "La date de début est requise")
    private Date dateDebut;

    @NotNull(message = "La date de fin est requise")
    private Date dateFin;

    @NotNull(message = "Le responsable est requis")
    private Long responsableId;

    private String description;

    @NotBlank(message = "L'année scolaire est requise")
    private String anneeScolaire;

    @NotNull(message = "Le statut est requis")
    private StatutSession statut;

    private List<Long> moduleIds;
}
