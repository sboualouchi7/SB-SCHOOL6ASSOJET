package ma.salman.sbschoolassojet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.salman.sbschoolassojet.enums.NumeroSeance;
import ma.salman.sbschoolassojet.enums.StatusSeance;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_id")
    private Long moduleId;
    @Column(name = "enseignant_id")
    private Long enseignantId;

    @Temporal(TemporalType.DATE)
    private Date date;

    private Time heureDebut;
    private Time heureFin;
    private String description;
    private boolean actif;

    @Enumerated(EnumType.STRING)
    private StatusSeance statut;

    @Enumerated(EnumType.STRING)
    private NumeroSeance numeroSeance;

    @ManyToOne
    @JoinColumn(name = "module_id", insertable = false, updatable = false)
    private Module module;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", insertable = false, updatable = false)
    private Enseignant enseignant;

    @OneToMany(mappedBy = "seance")
    private Set<Absence> absences = new HashSet<>();
}
