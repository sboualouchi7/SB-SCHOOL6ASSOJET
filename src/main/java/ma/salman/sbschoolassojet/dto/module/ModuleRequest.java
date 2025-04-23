package ma.salman.sbschoolassojet.dto.module;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.TypeModule;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRequest {
    @NotBlank(message = "Le libellé est requis")
    private String libelle;

    @Positive(message = "Le volume horaire doit être positif")
    private int volumeHoraire;

    @Positive(message = "Le seuil doit être positif")
    private int seuil;

    @Positive(message = "Le coefficient doit être positif")
    private float coefficient;

    @NotNull(message = "La classe est requise")
    private Long classeId;

    @NotNull(message = "Le niveau est requis")
    private Long niveauId;

    @NotNull(message = "L'enseignant est requis")
    private Long enseignantId;

    private String description;

    @NotNull(message = "Le type de module est requis")
    private TypeModule typeModule;
}
