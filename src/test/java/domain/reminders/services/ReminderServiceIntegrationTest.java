package domain.reminders.services;

import domain.reminders.dao.filtres.dto.ReminderDtoFilter;
import domain.reminders.dao.filtres.generic.CriteriaOperation;
import domain.reminders.dao.repository.ReminderRepository;
import domain.reminders.dao.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static domain.reminders.testdatafactories.ReminderTestDataFactory.getBasicReminder;
import static domain.reminders.testdatafactories.UserTestDataFactory.getBasicUser;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReminderServiceIntegrationTest {

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserRepository userRepository;


//    @DynamicPropertySource
//    static void postgresqlProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
//        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
//        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
//    }

    @Test
    void create_whenFilter_thenReturnAllRecords() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();

        var reminders = reminderService.filter(filter, CriteriaOperation.EQUALS);

        assertThat(reminders.size()).isEqualTo(5);
    }

    @Test
    void filter_whenTitleEqual_thenReturnTwoReminders() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();
        filter.setTitle(getBasicReminder().getTitle());

        var reminders = reminderService.filter(filter, CriteriaOperation.EQUALS);

        assertThat(reminders.size()).isEqualTo(2);
    }

    @Test
    void filter_whenTitleGreater_thenReturnThreeReminders() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();
        filter.setTitle(getBasicReminder().getTitle());

        var reminders = reminderService.filter(filter, CriteriaOperation.GREATER);

        assertThat(reminders.size()).isEqualTo(3);
    }

    @Test
    void filter_whenTitleLess_thenReturnThreeReminders() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();
        filter.setTitle(getBasicReminder().getTitle());

        var reminders = reminderService.filter(filter, CriteriaOperation.LESS);

        assertThat(reminders.size()).isEqualTo(0);
    }

    @Test
    void filter_whenDescriptionEqual_thenReturnTwoReminders() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();
        filter.setDescription(getBasicReminder().getDescription());

        var reminders = reminderService.filter(filter, CriteriaOperation.EQUALS);

        assertThat(reminders.size()).isEqualTo(2);
    }

    @Test
    void filter_whenDescriptionGreater_thenReturnThreeReminders() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();
        filter.setDescription(getBasicReminder().getDescription());

        var reminders = reminderService.filter(filter, CriteriaOperation.GREATER);

        assertThat(reminders.size()).isEqualTo(3);
    }

    @Test
    void filter_whenDescriptionLess_thenReturnThreeReminders() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();
        filter.setDescription(getBasicReminder().getDescription());

        var reminders = reminderService.filter(filter, CriteriaOperation.LESS);

        assertThat(reminders.size()).isEqualTo(0);
    }

    @Test
    void filter_whenDateEqual_thenReturnTwoReminders() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();
        filter.setDate(getBasicReminder().getRemind().toLocalDate());

        var reminders = reminderService.filter(filter, CriteriaOperation.EQUALS);

        assertThat(reminders.size()).isEqualTo(2);
    }

    @Test
    void filter_whenDateGreater_thenReturnTwoReminders() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();
        filter.setDate(getBasicReminder().getRemind().toLocalDate());

        var reminders = reminderService.filter(filter, CriteriaOperation.GREATER);

        assertThat(reminders.size()).isEqualTo(2);
    }

    @Test
    void filter_whenDateLess_thenReturnOneReminders() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();
        filter.setDate(getBasicReminder().getRemind().toLocalDate());

        var reminders = reminderService.filter(filter, CriteriaOperation.LESS);

        assertThat(reminders.size()).isEqualTo(1);
    }

    @Test
    void filter_whenTimeEqual_thenReturnZeroReminders() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();
        filter.setTime(getBasicReminder().getRemind().toLocalTime());

        var reminders = reminderService.filter(filter, CriteriaOperation.EQUALS);

        assertThat(reminders.size()).isEqualTo(0);
    }

    @Test
    void filter_whenTimeGreater_thenReturnTwoReminders() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();
        filter.setTime(getBasicReminder().getRemind().toLocalTime());

        var reminders = reminderService.filter(filter, CriteriaOperation.GREATER);

        assertThat(reminders.size()).isEqualTo(2);
    }

    @Test
    void filter_whenTimeLess_thenReturnOneReminders() {
        saveUserAndReminders();
        var filter = new ReminderDtoFilter();
        filter.setTime(getBasicReminder().getRemind().toLocalTime());

        var reminders = reminderService.filter(filter, CriteriaOperation.LESS);

        assertThat(reminders.size()).isEqualTo(3);
    }

    private void saveUserAndReminders() {
        userRepository.save(getBasicUser());
        userRepository.flush();

        var reminder1 = getBasicReminder().setId(null);
        reminder1.setShipped(false);
        reminder1.setRemind(reminder1.getRemind().minusMinutes(1));
        var reminder2 = getBasicReminder().setId(null);
        reminder2.setShipped(false);
        reminder2.setRemind(reminder2.getRemind().plusDays(3));
        var reminder3 = getBasicReminder().setId(null);
        reminder3.setShipped(false);
        reminder3.setRemind(reminder3.getRemind().minusDays(3));
        reminder3.setTitle(reminder3.getTitle() + "3");
        reminder3.setDescription(reminder3.getDescription() + "3");
        var reminder4 = getBasicReminder().setId(null);
        reminder4.setShipped(false);
        reminder4.setRemind(reminder4.getRemind().plusMonths(10));
        reminder4.setTitle(reminder4.getTitle() + "4");
        reminder4.setDescription(reminder4.getDescription() + "4");
        var reminder5 = getBasicReminder().setId(null);
        reminder5.setShipped(false);
        reminder5.setRemind(reminder5.getRemind().minusMinutes(10));
        reminder5.setTitle(reminder5.getTitle() + "5");
        reminder5.setDescription(reminder5.getDescription() + "5");
        reminderRepository.save(reminder1);
        reminderRepository.save(reminder2);
        reminderRepository.save(reminder3);
        reminderRepository.save(reminder4);
        reminderRepository.save(reminder5);
        reminderRepository.flush();
    }
}