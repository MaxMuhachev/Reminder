package domain.reminders.configs.telegram;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TelegramMessages {
    WELCOME_MESSAGE("Добро пожаловать! Я бот для напоминаний в telegram. Буду вам присылать напоминания"),
    WELCOME_MAIL_MESSAGE_START("""
            <b>Добро пожаловать в сервис Reminder!</b><br>
            Для активации подписки перейдите по ссылке и активируйте бота<br>
            <a href='https://t.me/reminderTelegaMailBot?start="""),
    WELCOME_MAIL_MESSAGE_END("'>ReminderTelegaMailBot</a>"),
    UNKNOWN_USER("Нет такого пользователя"),
    UNKNOWN_REGISTRATION("Сначала зарегестрируйтесь в приложении, потом я буду присылать напоминания");

    public static String getWelcomeMailMessage(String userId) {
        return WELCOME_MAIL_MESSAGE_START.message + userId + WELCOME_MAIL_MESSAGE_END.message;
    }

    private final String message;
}
