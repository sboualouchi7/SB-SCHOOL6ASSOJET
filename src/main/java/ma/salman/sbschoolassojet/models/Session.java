package ma.salman.sbschoolassojet.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.salman.sbschoolassojet.enums.StatutSession;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(name = "responsable_id")
    private Long responsableId;
    private String description;
    private String anneeScolaire;
    private boolean actif;

    @Enumerated(EnumType.STRING)
    private StatutSession statut;

    @OneToMany(mappedBy = "session")
    private Set<Evaluation> evaluations = new HashSet<>();

    @OneToMany(mappedBy = "session")
    private Set<SessionModule> sessionModules = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "responsable_id", insertable = false, updatable = false)
    private Utilisateur responsable;
}
