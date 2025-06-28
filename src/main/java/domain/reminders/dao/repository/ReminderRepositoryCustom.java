package domain.reminders.dao.repository;


import domain.reminders.dao.filtres.generic.CriteriaOperation;
import domain.reminders.dao.models.Reminder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Repository
public class ReminderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Set<String> ALLOWED_FIELDS = Set.of("title", "description");

    @SuppressWarnings("unchecked")
    public List<Reminder> getRemindersByDate(LocalDate remindDate, CriteriaOperation operation) {
        var sql = "SELECT * FROM reminders WHERE remind::date " + operation.getStringOperation() + " :remindDate";
        var query = entityManager.createNativeQuery(sql, Reminder.class);
        query.setParameter("remindDate", remindDate);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Reminder> getRemindersByTime(LocalTime remindTime, CriteriaOperation operation) {
        var remindDateTime = LocalDateTime.of(LocalDate.now(), remindTime);
        var sql = "SELECT * FROM reminders WHERE remind " + operation.getStringOperation() + " :remindDateTime";
        var query = entityManager.createNativeQuery(sql, Reminder.class);
        query.setParameter("remindDateTime", remindDateTime);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Reminder> getRemindersByString(String field, String value, CriteriaOperation operation) {
        if (!ALLOWED_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Invalid field: " + field + ". Allowed fields: " + ALLOWED_FIELDS);
        }
        var sql = "SELECT * FROM reminders WHERE " + field + operation.getStringOperation() + " :value";
        var query = entityManager.createNativeQuery(sql, Reminder.class);
        query.setParameter("value", value);
        return query.getResultList();
    }
}
