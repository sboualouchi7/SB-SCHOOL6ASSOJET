package ma.salman.sbschoolassojet.dto.absence;

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
public class AbsenceResponse {
    private Long id;
    private Long etudiantId;
    private Long seanceId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String motif;
    private String justification;
    private boolean validee;
    private Long moduleId;
    private String commentaire;
    private String nomEtudiant;
    private String moduleSeance;
    private String nomModule;  // Nouveau champ pour afficher le nom du module

}
