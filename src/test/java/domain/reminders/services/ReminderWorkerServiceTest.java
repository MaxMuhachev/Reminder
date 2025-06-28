package domain.reminders.services;

import domain.reminders.dao.repository.ReminderRepository;
import domain.reminders.exceptions.ReminderTelegramException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static domain.reminders.testdatafactories.ReminderTestDataFactory.getNewReminder;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class ReminderWorkerServiceTest {

    @Mock
    private ReminderRepository reminderRepository;
    @Mock
    private MailService mailService;
    @Mock
    private TelegramService telegramService;

    @InjectMocks
    private ReminderWorkerService reminderWorkerService;

    @Test
    void sendReminders_whenSend_thenCallAllServices() throws TelegramApiException {
        var expectedReminder = getNewReminder();
        when(reminderRepository.findRemindersToSend(any()))
                .thenReturn(singletonList(expectedReminder));

        reminderWorkerService.sendReminders();

        verify(mailService, times(1))
                .sendMailMessage(expectedReminder);
        verify(telegramService, times(1))
                .sendTelegramMessage(expectedReminder);
        verify(reminderRepository, times(1))
                .setIsShipped(expectedReminder.getId());
    }

    @Test
    void sendReminders_whenTelegramSendErr_thenThrowException() throws TelegramApiException {
        var reminder = getNewReminder();
        when(reminderRepository.findRemindersToSend(any()))
                .thenReturn(singletonList(reminder));
        doThrow(new TelegramApiException())
                .when(telegramService).sendTelegramMessage(reminder);

        assertThrows(ReminderTelegramException.class, () -> {
            reminderWorkerService.sendReminders();
        });

        verify(mailService, times(1))
                .sendMailMessage(reminder);
        verify(telegramService, times(1))
                .sendTelegramMessage(reminder);
        verify(reminderRepository, times(0))
                .setIsShipped(reminder.getId());
    }

}