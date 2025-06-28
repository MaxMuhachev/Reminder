package domain.reminders.services;

import domain.reminders.dao.mappers.RemindersMapper;
import domain.reminders.dao.repository.ReminderRepository;
import domain.reminders.dao.repository.ReminderRepositoryCustom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static domain.reminders.testdatafactories.OAuth2UserTestDataFactory.getBasicOAuth2User;
import static domain.reminders.testdatafactories.ReminderTestDataFactory.getBasicReminderDto;
import static domain.reminders.testdatafactories.ReminderTestDataFactory.getNewReminder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReminderServiceTest {

    @Mock
    private ReminderRepository reminderRepository;
    @Mock
    private ReminderRepositoryCustom reminderRepoCustom;
    @Mock
    private RemindersMapper remindersMapper;

    @InjectMocks
    private ReminderService reminderService;


    @Test
    void create_whenSave_thenRightFields() {
        var expectedReminderDto = getBasicReminderDto();
        var expectedReminder = getNewReminder();
        var user = getBasicOAuth2User();
        when(remindersMapper.dtoToEntity(expectedReminderDto, user))
                .thenReturn(expectedReminder);
        when(reminderRepository.save(expectedReminder))
                .thenReturn(expectedReminder);

        reminderService.create(expectedReminderDto, user);

        verify(remindersMapper).dtoToEntity(expectedReminderDto, user);
        verify(remindersMapper).entityToDto(expectedReminder);
        verify(reminderRepository).save(expectedReminder);
    }

    @Test
    void create_whenUpdate_thenRightFields() {
        var expectedReminderDto = getBasicReminderDto();
        var expectedReminder = getNewReminder();
        var user = getBasicOAuth2User();
        when(remindersMapper.dtoToEntity(expectedReminderDto, user))
                .thenReturn(expectedReminder);
        when(reminderRepository.save(expectedReminder))
                .thenReturn(expectedReminder);

        reminderService.create(expectedReminderDto, user);

        verify(reminderRepository).save(expectedReminder);
        verify(remindersMapper).dtoToEntity(expectedReminderDto, user);
        verify(remindersMapper).entityToDto(expectedReminder);
    }
}