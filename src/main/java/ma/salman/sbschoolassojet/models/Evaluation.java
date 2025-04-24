package ma.salman.sbschoolassojet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.salman.sbschoolassojet.enums.TypeEvaluation;

import java.util.Date;

@Entity
@Table(name = "evaluations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "etudiant_id")
    private Long etudiantId;
    @Column(name = "module_id")
    private Long moduleId;
    @Column(name = "enseignant_id")
    private Long enseignantId;
    @Column(name = "session_id")
    private Long sessionId;
    private float note;

    @Temporal(TemporalType.DATE)
    private Date dateEvaluation;

    private String commentaire;
    private boolean estValidee;

    @Enumerated(EnumType.STRING)
    private TypeEvaluation type;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", insertable = false, updatable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "module_id", insertable = false, updatable = false)
    private Module module;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", insertable = false, updatable = false)
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "session_id", insertable = false, updatable = false)
    private Session session;
}
