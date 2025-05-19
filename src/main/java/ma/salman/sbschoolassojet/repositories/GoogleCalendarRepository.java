package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.models.GoogleCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoogleCalendarRepository extends JpaRepository<GoogleCalendar, Long> {
    List<GoogleCalendar> findByTargetGroupAndActiveTrue(String targetGroup);
    List<GoogleCalendar> findByActiveTrue();
    boolean existsByCalendarId(String calendarId);
}
