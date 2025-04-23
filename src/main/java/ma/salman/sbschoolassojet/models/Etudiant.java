package ma.salman.sbschoolassojet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "etudiants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Etudiant extends Utilisateur{
    @Temporal(TemporalType.DATE)
    private Date dateInscription;

    private String filiere;
    private Long classeId;
    private Long niveauId;
    private String numeroEtudiant;
    private boolean actif;
    private String anneeScolaire;

    @ManyToOne
    @JoinColumn(name = "classe_id", insertable = false, updatable = false)
    private Classe classe;

    @OneToMany(mappedBy = "etudiant")
    private Set<Evaluation> evaluations = new HashSet<>();

    @OneToMany(mappedBy = "etudiant")
    private Set<Absence> absences = new HashSet<>();

    @OneToMany(mappedBy = "etudiant")
    private Set<Document> documents = new HashSet<>();

    @ManyToMany(mappedBy = "enfants")
    private Set<Parent> parents = new HashSet<>();
}
