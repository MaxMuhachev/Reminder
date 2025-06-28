package domain.reminders.services;

import domain.reminders.dao.dto.UserDto;
import domain.reminders.dao.mappers.UserMapper;
import domain.reminders.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto update(String telegramUsername, String userId) {
        userRepository.updateTelegramUsername(telegramUsername, userId);
        return userRepository.findById(userId)
                .map(userMapper::entityToDto)
                .orElse(null);
    }
}
