package ma.salman.sbschoolassojet.dto.Examen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.TypeExamen;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamenRequestDTO {

    @FutureOrPresent(message = "La date d'examen doit être aujourd'hui ou dans le futur")
    private LocalDate dateExamen;

    @NotNull(message = "Le type d'examen est obligatoire")
    private TypeExamen typeExamen;

    @NotNull(message = "L'ID de la classe est obligatoire")
    private Long classeId;
}