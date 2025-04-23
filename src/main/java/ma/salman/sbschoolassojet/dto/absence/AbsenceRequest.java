package ma.salman.sbschoolassojet.dto.absence;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotNull(message = "La date de début est requise")
    private Date dateDebut;

    @NotNull(message = "La date de fin est requise")
    private Date dateFin;

    private String motif;
    private byte[] justification;
    private String commentaire;
}
