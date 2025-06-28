package domain.reminders.exceptions;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ReminderException extends RuntimeException {

    public ReminderException(String message) {
        super("Error execute request with error: " + message);
    }
}
