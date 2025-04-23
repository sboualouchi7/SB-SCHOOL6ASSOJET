package ma.salman.sbschoolassojet.dto.document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.TypeDocument;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequest {
    @NotNull(message = "L'étudiant est requis")
    private Long etudiantId;

    @NotNull(message = "Le demandeur est requis")
    private Long demandeurId;

    private String commentaire;

    @NotNull(message = "Le type de document est requis")
    private TypeDocument type;
}
