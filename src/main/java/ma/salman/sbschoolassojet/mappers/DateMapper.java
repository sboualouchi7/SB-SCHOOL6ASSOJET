package ma.salman.sbschoolassojet.mappers;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @Named("dateToLocalDate")
    public LocalDate dateToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Named("localDateToDate")
    public Date localDateToDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    @Named("stringToLocalDate")
    public LocalDate stringToLocalDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    @Named("localDateToString")
    public String localDateToString(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }

    @Named("stringToLocalTime")
    public LocalTime stringToLocalTime(String time) {
        if (time == null || time.isEmpty()) {
            return null;
        }
        return LocalTime.parse(time, TIME_FORMATTER);
    }

    @Named("localTimeToString")
    public String localTimeToString(LocalTime time) {
        if (time == null) {
            return null;
        }
        return time.format(TIME_FORMATTER);
    }

    @Named("stringToLocalDateTime")
    public LocalDateTime stringToLocalDateTime(String dateTime) {
        if (dateTime == null || dateTime.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }

    @Named("localDateTimeToString")
    public String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    // Méthode pour LocalTime → Time SQL
    @Named("localTimeToSqlTime")
    public Time localTimeToSqlTime(LocalTime localTime) {
        if (localTime == null) {
            return null;
        }
        return Time.valueOf(localTime);
    }

    // Méthode pour Time SQL → LocalTime
    @Named("sqlTimeToLocalTime")
    public LocalTime sqlTimeToLocalTime(Time sqlTime) {
        if (sqlTime == null) {
            return null;
        }
        return sqlTime.toLocalTime();
    }
}