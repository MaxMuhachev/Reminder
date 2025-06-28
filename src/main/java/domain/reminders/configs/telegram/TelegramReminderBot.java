package domain.reminders.configs.telegram;

import domain.reminders.dao.models.User;
import domain.reminders.dao.repository.UserRepository;
import domain.reminders.exceptions.ReminderTelegramException;
import domain.reminders.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigInteger;

@Component
public class TelegramReminderBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.username}")
    private String botUsername;

    private final UserRepository userRepository;

    @Autowired
    public TelegramReminderBot(
            @Value("${telegram.bot.token}")
            String botToken,
            UserRepository userRepository
    ) {
        super(botToken);
        this.userRepository = userRepository;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            String username = update.getMessage().getFrom().getUserName();

            if (messageText.startsWith("/start")) {
                // Извлекаем параметр из команды /start
                String startParameter = messageText.replace("/start", "").trim();
                // User перешёл по ссылке, отправленной приложением
                String responseText = TelegramMessages.UNKNOWN_REGISTRATION.getMessage();
                if (StringUtil.isDigit(startParameter)) {
                    responseText = TelegramMessages.WELCOME_MESSAGE.getMessage();
                    User telegramUser = userRepository
                            .findById(startParameter)
                            .orElseThrow(
                                    () -> new ReminderTelegramException(
                                            TelegramMessages.UNKNOWN_USER.getMessage()
                                    )
                            );
                    // Добавляем telegram info о пользователе
                    if (telegramUser != null) {
                        telegramUser.setTelegramUsername(username != null ? username : "");
                        telegramUser.setTgChantId(chatId);
                        userRepository.save(telegramUser);
                    }
                }

                // Отправляем ответ
                var message = new SendMessage();
                message.setChatId(chatId.toString());
                message.setText(responseText);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}