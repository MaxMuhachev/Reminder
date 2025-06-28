package domain.reminders.testdatafactories;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;

public class OAuth2UserTestDataFactory {

    public static OAuth2User getBasicOAuth2User() {
        var attrKey = "id";
        var attrs = new HashMap<String, Object>();
        attrs.put(attrKey, UserTestDataFactory.getBasicUser().getId());
        return new DefaultOAuth2User(null, attrs, attrKey);
    }
}
