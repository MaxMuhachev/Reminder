package domain.reminders.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(
        name = "jsessionid",
        in = SecuritySchemeIn.COOKIE,
        type = SecuritySchemeType.APIKEY,
        paramName = "JSESSIONID"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Reminder API",
                description = "API for managing reminders to send telegram and mail", version = "1.0.0"
        )
)
public class OpenApiConfig {

}
