package domain.reminders.dao.mappers;

import domain.reminders.dao.models.User;
import domain.reminders.dao.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.math.BigInteger;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto entityToDto(User user);

    default User getUser(String userId, OAuth2User oAuth2User) {
        return new User()
                .setId(userId)
                .setEmail(oAuth2User.getAttribute("email"))
                .setName(oAuth2User.getAttribute("name"));
    }
}
