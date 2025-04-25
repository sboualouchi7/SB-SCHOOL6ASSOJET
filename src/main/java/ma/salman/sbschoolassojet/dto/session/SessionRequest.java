package ma.salman.sbschoolassojet.dto.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.StatutSession;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequest {
    @NotBlank(message = "Le nom est requis")
    private String nom;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "La date de début est requise")
    private LocalDate dateDebut;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "La date de fin est requise")
    private LocalDate dateFin;

    @NotNull(message = "Le responsable est requis")
    private Long responsableId;

    private String description;

    @NotBlank(message = "L'année scolaire est requise")
    private String anneeScolaire;

    @NotNull(message = "Le statut est requis")
    private StatutSession statut;

    private List<Long> moduleIds;
}
