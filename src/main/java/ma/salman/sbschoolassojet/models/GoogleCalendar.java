package ma.salman.sbschoolassojet.models;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "google_calendars")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GoogleCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "calendar_id", nullable = false, unique = true)
    private String calendarId;

    @Column(name = "target_group")
    private String targetGroup; // Peut être "ETUDIANT", "ENSEIGNANT", "CLASSE_GINF2", etc.

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "active")
    private boolean active = true;
}