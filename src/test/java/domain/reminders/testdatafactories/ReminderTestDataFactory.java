package domain.reminders.testdatafactories;

import domain.reminders.dao.dto.ReminderDto;
import domain.reminders.dao.models.Reminder;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class ReminderTestDataFactory {


    public static Reminder getNewReminder() {
        var user = UserTestDataFactory.getBasicUser();
        return new Reminder(
                null,
                "title",
                "descr",
                LocalDateTime.now(),
                user.getId(),
                null,
                false
        );
    }
    public static Reminder getBasicReminder() {
        var user = UserTestDataFactory.getBasicUser();
        return new Reminder(
                new BigInteger("1"),
                "title",
                "descr",
                LocalDateTime.now(),
                user.getId(),
                user,
                false
        );
    }

    public static ReminderDto getBasicReminderDto() {
        return new ReminderDto(
                new BigInteger("1"),
                "title",
                "descr",
                LocalDateTime.now(),
                false
        );
    }

}
