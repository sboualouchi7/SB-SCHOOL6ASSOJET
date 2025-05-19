package ma.salman.sbschoolassojet.models;

import jakarta.persistence.*;
import lombok.*;
import ma.salman.sbschoolassojet.enums.TypeExamen;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exam")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Examen {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate dateExamen;

    private TypeExamen typeExamen;

    @OneToMany(mappedBy = "examen")
    private Set<Module> modules = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;
}
