package ma.salman.sbschoolassojet.dto.seance;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.NumeroSeance;
import ma.salman.sbschoolassojet.enums.StatusSeance;

import java.sql.Time;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeanceRequest {
    @NotNull(message = "Le module est requis")
    private Long moduleId;

    @NotNull(message = "L'enseignant est requis")
    private Long enseignantId;

    @NotNull(message = "La date est requise")
    private Date date;

    @NotNull(message = "L'heure de début est requise")
    private Time heureDebut;

    @NotNull(message = "L'heure de fin est requise")
    private Time heureFin;

    private String description;

    @NotNull(message = "Le statut est requis")
    private StatusSeance statut;

    @NotNull(message = "Le numéro de séance est requis")
    private NumeroSeance numeroSeance;
}
