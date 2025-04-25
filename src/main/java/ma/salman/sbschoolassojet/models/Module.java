package ma.salman.sbschoolassojet.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import ma.salman.sbschoolassojet.enums.TypeModule;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "modules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private int volumeHoraire;
    private int seuil;
    private float coefficient;
    @Column(name = "classe_id")
    private Long classeId;
    private Long niveauId;
    @Column(name = "enseignant_id")
    private Long enseignantId;
    private boolean actif;
    private String description;

    @Enumerated(EnumType.STRING)
    private TypeModule typeModule;


    private LocalDate dateCreation;


    private LocalDate dateModification;

    @ManyToOne
    @JoinColumn(name = "classe_id", insertable = false, updatable = false)
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", insertable = false, updatable = false)
    private Enseignant enseignant;

    @OneToMany(mappedBy = "module")
    private Set<Seance> seances = new HashSet<>();

    @OneToMany(mappedBy = "module")
    private Set<Evaluation> evaluations = new HashSet<>();

    @OneToMany(mappedBy = "module")
    private Set<SessionModule> sessionModules = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDate.now();
        this.dateModification = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModification = LocalDate.now();
    }
}
