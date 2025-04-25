package ma.salman.sbschoolassojet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "session_module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "session_id")
    private Long sessionId;
    @Column(name = "module_id")
    private Long moduleId;


    private LocalDate dateAjout;

    private int ordre;

    @ManyToOne
    @JoinColumn(name = "session_id", insertable = false, updatable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "module_id", insertable = false, updatable = false)
    private Module module;
}
