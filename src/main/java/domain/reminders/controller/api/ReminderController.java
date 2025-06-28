package domain.reminders.controller.api;

import domain.reminders.dao.dto.ReminderDto;
import domain.reminders.dao.filtres.dto.ReminderDtoFilter;
import domain.reminders.dao.filtres.generic.CriteriaOperation;
import domain.reminders.exceptions.ReminderException;
import domain.reminders.services.ReminderService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Tag(name = "Работа с напоминаниями", description = "CRUD напоминаний и их вывод, сортировка, фильтрация")
@SecurityRequirement(name = "jsessionid")
@RestController
@RequiredArgsConstructor
@RequestMapping("domain/api/v1/reminder")
public class ReminderController {

    private final ReminderService remindersService;

    @PostMapping("/create")
    public ReminderDto create(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestBody ReminderDto reminder
    ) {
        return remindersService.create(reminder, principal);
    }

    @PutMapping("/update")
    public ReminderDto update(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestBody ReminderDto reminder
    ) {
        return remindersService.update(reminder, principal);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(
            @RequestParam BigInteger reminderId
    ) {
        remindersService.delete(reminderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sort")
    public List<ReminderDto> sort(
            @Schema(example = "{\"page\": 0,\n\"size\": 100,\n\"sort\":\"id,ASC\"}")
            @PageableDefault(sort = {"id"}, size = 100)
            Pageable pageable
    ) {
        return remindersService.findAll(pageable);
    }

    @GetMapping("/filter")
    public List<ReminderDto> filter(
            ReminderDtoFilter filter,
            CriteriaOperation operation
    ) {
        return remindersService.filter(filter, operation);
    }

    @GetMapping("/list")
    public List<ReminderDto> list(
            @Schema(example = "{\"page\": 0,\n\"size\": 100,\n\"sort\":\"id,ASC\"}")
            @PageableDefault(sort = {"id"}, size = 100)
            Pageable pageable
    ) {
        return remindersService.findAll(pageable);
    }
}