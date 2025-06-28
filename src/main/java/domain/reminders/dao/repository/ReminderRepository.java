package domain.reminders.dao.repository;

import domain.reminders.dao.models.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, BigInteger> {

    @Query("select r from Reminder r where r.remind <= :now and r.shipped = false")
    List<Reminder> findRemindersToSend(LocalDateTime now);

    @Modifying
    @Query("update Reminder r set r.shipped = true where r.id = :id")
    void setIsShipped(BigInteger id);
}
