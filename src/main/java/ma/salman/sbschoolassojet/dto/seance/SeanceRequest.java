package ma.salman.sbschoolassojet.dto.seance;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.NumeroSeance;
import ma.salman.sbschoolassojet.enums.StatusSeance;


import java.time.LocalDate;
import java.time.LocalTime;
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    @NotNull(message = "L'heure de début est requise")
    private LocalTime heureDebut;

    @JsonFormat(pattern = "HH:mm:ss")
    @NotNull(message = "L'heure de fin est requise")
    private LocalTime heureFin;

    private String description;

    @NotNull(message = "Le statut est requis")
    private StatusSeance statut;

    @NotNull(message = "Le numéro de séance est requis")
    private NumeroSeance numeroSeance;
}
