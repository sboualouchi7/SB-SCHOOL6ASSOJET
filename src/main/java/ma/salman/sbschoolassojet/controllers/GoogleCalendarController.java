package ma.salman.sbschoolassojet.controllers;
import ma.salman.sbschoolassojet.models.GoogleCalendar;
import ma.salman.sbschoolassojet.services.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/calendars")
@CrossOrigin(origins = "*")
public class GoogleCalendarController {

    @Autowired
    private GoogleCalendarService calendarService;

    @GetMapping
    public ResponseEntity<List<GoogleCalendar>> getAllCalendars() {
        return ResponseEntity.ok(calendarService.getAllCalendars());
    }

    @GetMapping("/group/{targetGroup}")
    public ResponseEntity<List<GoogleCalendar>> getCalendarsByTargetGroup(@PathVariable String targetGroup) {
        return ResponseEntity.ok(calendarService.getCalendarsByTargetGroup(targetGroup));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoogleCalendar> getCalendarById(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.getCalendarById(id));
    }

    @PostMapping
    public ResponseEntity<GoogleCalendar> createCalendar(@RequestBody GoogleCalendar calendar) {
        try {
            GoogleCalendar createdCalendar = calendarService.createCalendar(calendar);
            return new ResponseEntity<>(createdCalendar, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoogleCalendar> updateCalendar(@PathVariable Long id, @RequestBody GoogleCalendar calendar) {
        return ResponseEntity.ok(calendarService.updateCalendar(id, calendar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendar(@PathVariable Long id) {
        calendarService.deleteCalendar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/extract-id")
    public ResponseEntity<Map<String, String>> extractCalendarId(@RequestBody Map<String, String> request) {
        String url = request.get("url");
        String calendarId = extractIdFromUrl(url);
        return ResponseEntity.ok(Map.of("calendarId", calendarId));
    }

    // Méthode utilitaire pour extraire l'ID de calendrier d'une URL
    private String extractIdFromUrl(String url) {
        // Essayer de trouver l'ID dans différents formats d'URL
        // Format 1: ?cid=...@group.calendar.google.com
        String cidPattern = "[?&]cid=([^&]+)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(cidPattern);
        java.util.regex.Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        }

        // Format 2: /calendar/...?src=...@group.calendar.google.com
        String srcPattern = "[?&]src=([^&]+)";
        pattern = java.util.regex.Pattern.compile(srcPattern);
        matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        }

        // Si aucun format connu n'est trouvé, retourner l'URL d'origine
        return url;
    }
}