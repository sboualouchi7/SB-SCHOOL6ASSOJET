package ma.salman.sbschoolassojet.dto.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.StatutSession;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponse {
    private Long id;
    private String nom;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateDebut;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateFin;
    private Long responsableId;
    private String description;
    private String anneeScolaire;
    private boolean actif;
    private StatutSession statut;
    private String nomResponsable;
}
