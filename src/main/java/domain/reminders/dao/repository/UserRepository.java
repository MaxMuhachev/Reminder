package domain.reminders.dao.repository;

import domain.reminders.dao.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Modifying
    @Query("update User u set u.telegramUsername = :telegramUsername where u.id = :id")
    void updateTelegramUsername(String telegramUsername, String id);
}
