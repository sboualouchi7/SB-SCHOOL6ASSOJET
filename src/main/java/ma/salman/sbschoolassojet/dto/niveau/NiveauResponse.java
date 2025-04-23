package ma.salman.sbschoolassojet.dto.niveau;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NiveauResponse {
    private Long id;
    private String label;
    private String description;
    private int ordre;
    private boolean actif;
}
