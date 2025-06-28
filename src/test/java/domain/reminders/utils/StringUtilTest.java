package domain.reminders.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class StringUtilTest {

    @Test
    void isDigit_whenDigit_thenTrue() {
        assertTrue(StringUtil.isDigit("1"));
    }

    @Test
    void isDigit_whenDigitWithChar_thenFalse() {
        assertFalse(StringUtil.isDigit("1a"));
    }

    @Test
    void isDigit_whenCharsWithDigit_thenFalse() {
        assertFalse(StringUtil.isDigit("a1a"));
    }

    @Test
    void isDigit_whenDigits_thenTrue() {
        assertTrue(StringUtil.isDigit("22123"));
    }

    @Test
    void isDigit_whenEmptyString_thenFalse() {
        assertFalse(StringUtil.isDigit(""));
    }
}