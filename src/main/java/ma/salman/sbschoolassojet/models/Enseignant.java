package ma.salman.sbschoolassojet.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "enseignants")
@Getter
@Setter

@SuperBuilder
public class Enseignant extends Utilisateur{
    public Enseignant() {
        super();
    }
    private String numeroCarte;
    private Long departementId;


    private LocalDate dateEmbauche;

    private String specialite;
}
