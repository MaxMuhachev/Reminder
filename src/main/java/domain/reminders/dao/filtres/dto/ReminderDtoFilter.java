package domain.reminders.dao.filtres.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReminderDtoFilter {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate date;

    @Schema(example = "12:00:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    LocalTime time;

    String title;

    String description;
}
