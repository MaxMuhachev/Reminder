package domain.reminders.services;

import domain.reminders.dao.dto.ReminderDto;
import domain.reminders.dao.filtres.dto.ReminderDtoFilter;
import domain.reminders.dao.filtres.generic.CriteriaOperation;
import domain.reminders.dao.mappers.RemindersMapper;
import domain.reminders.dao.models.Reminder;
import domain.reminders.dao.repository.ReminderRepository;
import domain.reminders.dao.repository.ReminderRepositoryCustom;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final ReminderRepositoryCustom reminderRepoCustom;
    private final RemindersMapper remindersMapper;

    public ReminderDto create(@Nonnull ReminderDto reminderDto, @Nonnull OAuth2User principal) {
        var reminderToCreate = remindersMapper.dtoToEntity(reminderDto, principal);
        reminderToCreate.setId(null);
        reminderToCreate.setShipped(false);
        reminderToCreate.setUserId(principal.getName());
        return remindersMapper.entityToDto(
                reminderRepository.save(reminderToCreate)
        );
    }

    public ReminderDto update(@Nonnull ReminderDto reminderDto, @Nonnull OAuth2User principal) {
        var reminder = reminderRepository.save(remindersMapper.dtoToEntity(reminderDto, principal));
        return remindersMapper.entityToDto(reminder);
    }

    public void delete(@Nonnull BigInteger reminderId) {
        reminderRepository.deleteById(reminderId);
    }

    public List<ReminderDto> filter(@Nonnull ReminderDtoFilter filter, @Nonnull CriteriaOperation operation) {
        if (filter.getTitle() == null && filter.getDescription() == null && filter.getDate() == null && filter.getTime() == null) {
            return reminderRepository.findAll().stream()
                    .map(remindersMapper::entityToDto)
                    .toList();
        }
        if (filter.getTitle() != null) {
            return reminderRepoCustom.getRemindersByString("title", filter.getTitle(), operation).stream()
                    .map(remindersMapper::entityToDto)
                    .toList();
        }
        if (filter.getDescription() != null) {
            return reminderRepoCustom.getRemindersByString("description", filter.getDescription(), operation).stream()
                    .map(remindersMapper::entityToDto)
                    .toList();
        }
        if (filter.getDate() != null) {
            return reminderRepoCustom.getRemindersByDate(filter.getDate(), operation).stream()
                    .map(remindersMapper::entityToDto)
                    .toList();
        }
        if (filter.getTime() != null) {
            return reminderRepoCustom.getRemindersByTime(filter.getTime(), operation).stream()
                    .map(remindersMapper::entityToDto)
                    .toList();
        }
        return new ArrayList<>();
    }

    public List<ReminderDto> findAll(Pageable pageable) {
        return reminderRepository.findAll(pageable)
                .map(remindersMapper::entityToDto)
                .toList();
    }
}
