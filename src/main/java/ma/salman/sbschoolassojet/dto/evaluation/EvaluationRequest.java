package ma.salman.sbschoolassojet.dto.evaluation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.TypeEvaluation;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationRequest {
    @NotNull(message = "L'étudiant est requis")
    private Long etudiantId;

    @NotNull(message = "Le module est requis")
    private Long moduleId;

    @NotNull(message = "L'enseignant est requis")
    private Long enseignantId;

    @NotNull(message = "La session est requise")
    private Long sessionId;

    @PositiveOrZero(message = "La note doit être positive ou nulle")
    private float note;

    @NotNull(message = "La date d'évaluation est requise")
    private Date dateEvaluation;

    private String commentaire;

    @NotNull(message = "Le type d'évaluation est requis")
    private TypeEvaluation type;
}
