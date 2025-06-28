package domain.reminders.dao.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDto {

    @NotNull
    private String id;

    @Length(max = 100)
    private String name;

    @Email
    private String email;

    @NotBlank
    private String telegramUsername;
}