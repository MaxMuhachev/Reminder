package domain.reminders.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

    public static boolean isDigit(String input) {
        return input.matches("[0-9]+");
    }
}
