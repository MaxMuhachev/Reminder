package domain.reminders.services;

import domain.reminders.configs.telegram.TelegramReminderBot;
import domain.reminders.dao.models.Reminder;
import domain.reminders.dao.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class TelegramService {

    private final TelegramReminderBot reminderBot;

    public void sendTelegramMessage(Reminder reminder) throws TelegramApiException {
        if (reminder.getUser().getTgChantId() != null) {
            var message = new SendMessage();
            message.setChatId(reminder.getUser().getTgChantId());
            message.setParseMode(ParseMode.HTML);
            message.setText("<b>Напоминание:" + reminder.getTitle() + "</b>\n" + reminder.getDescription());
            reminderBot.execute(message);
        }
    }
}
