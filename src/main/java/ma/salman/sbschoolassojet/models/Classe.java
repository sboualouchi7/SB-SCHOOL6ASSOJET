package ma.salman.sbschoolassojet.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(name = "niveau_id")
    private Long niveauId;
    private String anneeScolaire;
    private int capacite;
    private boolean actif;

    private LocalDate dateCreation;

    @ManyToOne
    @JoinColumn(name = "niveau_id", insertable = false, updatable = false)
    private Niveau niveau;

    @OneToMany(mappedBy = "classe")
    private Set<Etudiant> etudiants = new HashSet<>();

    @OneToMany(mappedBy = "classe")
    private Set<Module> modules = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDate.now();
    }
}
