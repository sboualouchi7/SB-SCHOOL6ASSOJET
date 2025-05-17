package ma.salman.sbschoolassojet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "absences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "etudiant_id")
    private Long etudiantId;

    @Column(name = "seance_id")
    private Long seanceId;

    // Nouveau champ moduleId
    @Column(name = "module_id")
    private Long moduleId;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private String motif;

    private String justification;

    private boolean validee;

    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", insertable = false, updatable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "seance_id", insertable = false, updatable = false)
    private Seance seance;

    // Nouvelle relation avec Module
    @ManyToOne
    @JoinColumn(name = "module_id", insertable = false, updatable = false)
    private Module module;
}