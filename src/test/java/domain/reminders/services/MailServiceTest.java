package domain.reminders.services;

import domain.reminders.dao.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static domain.reminders.testdatafactories.ReminderTestDataFactory.getBasicReminder;
import static domain.reminders.testdatafactories.UserTestDataFactory.getBasicUser;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MailServiceTest {

    @Mock
    private JavaMailSender mailSender;
    @Mock
    private MimeMessageHelper mimeMessageHelper;

    @InjectMocks
    private MailService mailService;


    @Test
    void sendMailMessage_whenCorrectMessage_thenSendRightMessage() {
        var expectedReminder = getBasicReminder();
        var message = new SimpleMailMessage();
        message.setFrom(null);
        message.setTo(expectedReminder.getUser().getEmail());
        message.setSubject(expectedReminder.getTitle());
        message.setText(expectedReminder.getDescription());

        mailService.sendMailMessage(expectedReminder);

        verify(mailSender).send(message);
    }

    @Test
    void sendMailMessage_whenCorrectMessageWithoutUser_thenNPE() {
        var expectedReminder = getBasicReminder();
        expectedReminder.setUser(null);

        assertThrows(NullPointerException.class, () -> {
            mailService.sendMailMessage(expectedReminder);
        });
    }

    @Test
    void sendWelcomeMessage_whenCorrectMessage_thenSendRightMessage() throws MessagingException {
        var user = getBasicUser();
        MimeMessage message = null;
        when(mimeMessageHelper.getMimeMessage())
                .thenReturn(message);

        mailService.sendWelcomeMessage(user);

        verify(mailSender).send(message);
        verify(mimeMessageHelper).setTo(user.getEmail());
    }

    @Test
    void sendWelcomeMessage_whenCorrectMessageWithoutUser_thenNPE() throws MessagingException {
        User user = null;
        MimeMessage message = null;
        when(mimeMessageHelper.getMimeMessage())
                .thenReturn(message);

        assertThrows(NullPointerException.class, () -> {
            mailService.sendWelcomeMessage(user);
        });

        verifyNoMoreInteractions(mailSender);
    }


}