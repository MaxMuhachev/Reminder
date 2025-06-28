package domain.reminders.services;

import domain.reminders.dao.repository.ReminderRepository;
import domain.reminders.exceptions.ReminderTelegramException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ReminderWorkerService {

    private final ReminderRepository reminderRepository;
    private final MailService mailService;
    private final TelegramService telegramService;

    public void sendReminders() {
        var reminders = reminderRepository.findRemindersToSend(LocalDateTime.now());
        reminders.forEach(reminder -> {
            mailService.sendMailMessage(reminder);
            try {
                telegramService.sendTelegramMessage(reminder);
            } catch (TelegramApiException ex) {
                throw new ReminderTelegramException(ex);
            }
            reminderRepository.setIsShipped(reminder.getId());
        });
    }


}
