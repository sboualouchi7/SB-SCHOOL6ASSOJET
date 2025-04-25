package ma.salman.sbschoolassojet.dto.evaluation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.TypeEvaluation;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationResponse {
    private Long id;
    private Long etudiantId;
    private Long moduleId;
    private Long enseignantId;
    private Long sessionId;
    private float note;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateEvaluation;
    private String commentaire;
    private boolean estValidee;
    private TypeEvaluation type;
    private String nomEtudiant;
    private String libelleModule;
    private String nomEnseignant;
    private String nomSession;
}
