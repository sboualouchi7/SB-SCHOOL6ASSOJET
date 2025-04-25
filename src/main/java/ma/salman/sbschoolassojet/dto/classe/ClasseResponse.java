package ma.salman.sbschoolassojet.dto.classe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClasseResponse {
    private Long id;
    private String nom;
    private Long niveauId;
    private String anneeScolaire;
    private int capacite;
    private boolean actif;
    private LocalDate dateCreation;
    private String labelNiveau;
}
