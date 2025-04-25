package ma.salman.sbschoolassojet.dto.absence;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbsenceRequest {
    @NotNull(message = "L'étudiant est requis")
    private Long etudiantId;

    @NotNull(message = "La séance est requise")
    private Long seanceId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "La date de début est requise")
    private LocalDate dateDebut;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "La date de fin est requise")
    private LocalDate dateFin;

    private String motif;
    private String justification;
    private String commentaire;
}
