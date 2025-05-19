package ma.salman.sbschoolassojet.services;


import ma.salman.sbschoolassojet.models.GoogleCalendar;
import ma.salman.sbschoolassojet.repositories.GoogleCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GoogleCalendarService {

    @Autowired
    private GoogleCalendarRepository calendarRepository;

    public List<GoogleCalendar> getAllCalendars() {
        return calendarRepository.findByActiveTrue();
    }

    public List<GoogleCalendar> getCalendarsByTargetGroup(String targetGroup) {
        return calendarRepository.findByTargetGroupAndActiveTrue(targetGroup);
    }

    public GoogleCalendar createCalendar(GoogleCalendar calendar) {
        // Vérifier si l'ID de calendrier existe déjà
        if (calendarRepository.existsByCalendarId(calendar.getCalendarId())) {
            throw new IllegalArgumentException("Un calendrier avec cet ID existe déjà");
        }

        calendar.setCreatedAt(LocalDateTime.now());
        calendar.setUpdatedAt(LocalDateTime.now());
        calendar.setActive(true);

        return calendarRepository.save(calendar);
    }

    public GoogleCalendar updateCalendar(Long id, GoogleCalendar calendarDetails) {
        GoogleCalendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calendrier non trouvé avec l'ID: " + id));

        calendar.setName(calendarDetails.getName());
        calendar.setDescription(calendarDetails.getDescription());
        calendar.setCalendarId(calendarDetails.getCalendarId());
        calendar.setTargetGroup(calendarDetails.getTargetGroup());
        calendar.setUpdatedAt(LocalDateTime.now());

        return calendarRepository.save(calendar);
    }

    public void deleteCalendar(Long id) {
        GoogleCalendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calendrier non trouvé avec l'ID: " + id));

        // Suppression logique
        calendar.setActive(false);
        calendar.setUpdatedAt(LocalDateTime.now());
        calendarRepository.save(calendar);
    }

    public GoogleCalendar getCalendarById(Long id) {
        return calendarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calendrier non trouvé avec l'ID: " + id));
    }
}