package ma.salman.sbschoolassojet.dto.absence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbsenceResponse {
    private Long id;
    private Long etudiantId;
    private Long seanceId;
    private Date dateDebut;
    private Date dateFin;
    private String motif;
    private byte[] justification;
    private boolean validee;
    private String commentaire;
    private String nomEtudiant;
    private String moduleSeance;
}
