package domain.reminders.controller.api;

import domain.reminders.dao.dto.UserDto;
import domain.reminders.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Изменение пользователя", description = "Изменение тега в telegram пользователя")
@SecurityRequirement(name = "jsessionid")
@RestController
@RequiredArgsConstructor
@RequestMapping("domain/api/v1/user")
public class UserController {

    private final UserService userService;

    @PatchMapping("/update")

    public UserDto update(
            @RequestParam
            String telegramUsername,
            @AuthenticationPrincipal OAuth2User principal
    ) {
        return userService.update(telegramUsername, principal.getName());
    }
}
