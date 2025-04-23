package ma.salman.sbschoolassojet.dto.classe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClasseRequest {
    @NotBlank(message = "Le nom est requis")
    private String nom;

    @NotNull(message = "Le niveau est requis")
    private Long niveauId;

    @NotBlank(message = "L'année scolaire est requise")
    private String anneeScolaire;

    @Positive(message = "La capacité doit être positive")
    private int capacite;
}
