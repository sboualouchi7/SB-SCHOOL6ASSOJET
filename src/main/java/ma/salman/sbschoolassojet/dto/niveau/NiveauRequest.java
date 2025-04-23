package ma.salman.sbschoolassojet.dto.niveau;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NiveauRequest {
    @NotBlank(message = "Le label est requis")
    private String label;

    private String description;

    @Positive(message = "L'ordre doit être positif")
    private int ordre;
}
