package ma.salman.sbschoolassojet.dto.sessionmodule;

import  jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionModuleRequest {
    @NotNull(message = "La session est requise")
    private Long sessionId;

    @NotNull(message = "Le module est requis")
    private Long moduleId;

    @Positive(message = "L'ordre doit être positif")
    private int ordre;
}
