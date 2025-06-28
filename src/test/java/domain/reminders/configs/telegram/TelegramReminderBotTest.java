package domain.reminders.configs.telegram;

import domain.reminders.RemindersApplication;
import domain.reminders.dao.models.User;
import domain.reminders.dao.repository.UserRepository;
import domain.reminders.exceptions.ReminderTelegramException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static domain.reminders.testdatafactories.TelegramUserTestDataFactory.getBasicUpdateInvited;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = RemindersApplication.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class TelegramReminderBotTest {

    @MockitoBean
    private UserRepository userRepository;

    @MockitoSpyBean
    private TelegramReminderBot telegramReminderBot;

    @Test
    void onUpdateReceived_whenNoStartCommand_thenSendUnknownRegistrations() throws TelegramApiException {
        var updateInvited = getBasicUpdateInvited();
        updateInvited.getMessage().setText("User что-то написал");
        var message = new SendMessage();
        message.setChatId(updateInvited.getMessage().getChatId());
        message.setText(TelegramMessages.UNKNOWN_REGISTRATION.getMessage());

        telegramReminderBot.onUpdateReceived(updateInvited);

        verifyNoInteractions(userRepository);
    }

    @Test
    void onUpdateReceived_whenStartCommandWithoutParams_thenSendUnknownRegistrations() throws TelegramApiException {
        var updateInvited = getBasicUpdateInvited();
        var message = new SendMessage();
        message.setChatId(updateInvited.getMessage().getChatId());
        message.setText(TelegramMessages.UNKNOWN_REGISTRATION.getMessage());
        doReturn(new Message())
                .when(telegramReminderBot)
                .execute(message);

        telegramReminderBot.onUpdateReceived(updateInvited);

        verify(telegramReminderBot).execute(message);
        verifyNoInteractions(userRepository);
    }

    @Test
    void onUpdateReceived_whenRightParamsAndUserNotFound_thenThrowTelegramEx() throws TelegramApiException {
        var userId = "1";
        var updateInvited = getBasicUpdateInvited();
        updateInvited.getMessage().setText(updateInvited.getMessage().getText() + " " + userId);
        doReturn(Optional.empty())
                .when(userRepository).findById(userId);

        assertThrows(ReminderTelegramException.class, () -> {
            telegramReminderBot.onUpdateReceived(updateInvited);
        });

        verify(userRepository).findById(userId);
    }

    @Test
    void onUpdateReceived_whenRightParamsAndUserFound_thenSendWelcomeMessage() throws TelegramApiException {
        var userId = "1";
        var userDb = new User().setId(userId);
        var updateInvited = getBasicUpdateInvited();
        updateInvited.getMessage().setText(updateInvited.getMessage().getText() + " " + userId);
        var message = new SendMessage();
        message.setChatId(updateInvited.getMessage().getChatId());
        message.setText(TelegramMessages.WELCOME_MESSAGE.getMessage());
        doReturn(new Message())
                .when(telegramReminderBot)
                .execute(message);
        doReturn(Optional.of(userDb))
                .when(userRepository).findById(userId);

        telegramReminderBot.onUpdateReceived(updateInvited);

        verify(userRepository).findById(userId);
        userDb.setTelegramUsername(updateInvited.getMessage().getFrom().getUserName());
        userDb.setTgChantId(updateInvited.getMessage().getChatId());
        verify(userRepository).save(userDb);
        verify(telegramReminderBot).execute(message);
    }
}
