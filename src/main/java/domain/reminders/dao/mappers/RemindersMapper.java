package domain.reminders.dao.mappers;

import domain.reminders.dao.models.Reminder;
import domain.reminders.dao.dto.ReminderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Mapper(componentModel = "spring")
public interface RemindersMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "userId", expression = "java(user.getName())")
    Reminder dtoToEntity(ReminderDto reminderDto, OAuth2User user);

    ReminderDto entityToDto(Reminder reminder);
}
