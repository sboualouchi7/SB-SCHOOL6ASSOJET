package ma.salman.sbschoolassojet.dto.seance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.NumeroSeance;
import ma.salman.sbschoolassojet.enums.StatusSeance;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeanceResponse {
    private Long id;
    private Long moduleId;
    private Long enseignantId;
    private Date date;
    private Time heureDebut;
    private Time heureFin;
    private String description;
    private boolean actif;
    private StatusSeance statut;
    private NumeroSeance numeroSeance;
    private String libelleModule;
    private String nomEnseignant;
}
