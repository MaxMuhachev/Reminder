package domain.reminders.services;

import domain.reminders.configs.telegram.TelegramMessages;
import domain.reminders.dao.models.Reminder;
import domain.reminders.dao.models.User;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import static domain.reminders.configs.telegram.TelegramMessages.*;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${mail.email}")
    private String email;

    private final JavaMailSender mailSender;
    private final MimeMessageHelper mimeMessageHelper;

    public void sendMailMessage(Reminder reminder) {
        var message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(reminder.getUser().getEmail());
        message.setSubject(reminder.getTitle());
        message.setText(reminder.getDescription());
        mailSender.send(message);
    }

    public void sendWelcomeMessage(User user) throws MessagingException {
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject(WELCOME_MESSAGE.getMessage());
        mimeMessageHelper.setText(getWelcomeMailMessage(user.getId()), true);
        mailSender.send(mimeMessageHelper.getMimeMessage());
    }
}
