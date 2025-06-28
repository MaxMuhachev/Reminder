package domain.reminders.configs.security;

import domain.reminders.dao.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

/**
 * wrapper around sfGuardUser, used by spring security
 */
/**
 * Class for getting user info from OAuth2.
 */
@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails, OAuth2User {
    private User user;

    @Override
    public Map<String, Object> getAttributes() {
        var attr = new HashMap<String, Object>();
        attr.put("id", user.getId());
        attr.put("name", user.getName());
        attr.put("email", user.getEmail());
        return attr;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return singletonList(() -> "USER");
    }

    @Override
    public String getName() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
