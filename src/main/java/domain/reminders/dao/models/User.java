package domain.reminders.dao.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Length;

import java.math.BigInteger;
import java.util.List;


@Table(name = "users")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @ToString.Include
    private String id;

    @Length(max = 100)
    private String name;

    @Email
    @NotBlank
    @Length(max = 100)
    private String email;

    @Length(min = 3, max = 100)
    @Column(name = "telegram_username")
    private String telegramUsername;

    @Column(name = "tg_chat_id")
    private Long tgChantId;

    @JsonManagedReference
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Reminder> reminders;
}
