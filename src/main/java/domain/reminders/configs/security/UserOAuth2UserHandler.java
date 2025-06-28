package domain.reminders.configs.security;

import domain.reminders.dao.mappers.UserMapper;
import domain.reminders.dao.repository.UserRepository;
import domain.reminders.services.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;


import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public final class UserOAuth2UserHandler implements Consumer<OAuth2User> {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Override
    public void accept(OAuth2User user) {
        var userId = user.getName();
        // Capture user in a local data store on first authentication
        var userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            var entityUser = userMapper.getUser(userId, user);
            userRepository.save(entityUser);
            try {
                mailService.sendWelcomeMessage(entityUser);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
