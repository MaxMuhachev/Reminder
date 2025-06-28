package domain.reminders.exceptions;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.Supplier;

public class ReminderTelegramException extends RuntimeException {

    public ReminderTelegramException(TelegramApiException message) {
        super("Error send message to telegram" + message);
    }

    public ReminderTelegramException(String message) {
        super("Error send message to telegram" + message);
    }
}
