package ma.salman.sbschoolassojet.dto.Examen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.TypeExamen;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamenResponseDTO {

    private Integer id;
    private LocalDate dateExamen;
    private TypeExamen typeExamen;
    private Long classeId;
    private String classeNom;
    private List<ModuleSimpleDTO> modules;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModuleSimpleDTO {
        private Long id;
        private String libelle;
    }
}