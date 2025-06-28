package domain.reminders.dao.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ReminderDto {

    @NotNull
    private BigInteger id;

    @NotBlank
    @Length(max = 255)
    private String title;

    @NotBlank
    @Length(max = 4096)
    private String description;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime remind;

    @Schema(hidden = true, example = "false", accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean shipped;
}