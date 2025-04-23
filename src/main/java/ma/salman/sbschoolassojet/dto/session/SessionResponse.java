package ma.salman.sbschoolassojet.dto.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.StatutSession;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponse {
    private Long id;
    private String nom;
    private Date dateDebut;
    private Date dateFin;
    private Long responsableId;
    private String description;
    private String anneeScolaire;
    private boolean actif;
    private StatutSession statut;
    private String nomResponsable;
}
