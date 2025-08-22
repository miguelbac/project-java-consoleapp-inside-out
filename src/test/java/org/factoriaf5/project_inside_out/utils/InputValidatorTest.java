package org.factoriaf5.project_inside_out.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    @Test
    void parseDate_ShouldReturnValidDate() {
        LocalDate date = InputValidator.parseDate("25/12/2020");
        assertEquals(LocalDate.of(2020, 12, 25), date);
    }

    @Test
    void parseDate_InvalidFormat_ShouldReturnNull() {
        assertNull(InputValidator.parseDate("2020-12-25"));
        assertNull(InputValidator.parseDate(""));
    }

    @Test
    void isValidEmotionOption_ShouldValidateRange() {
        assertTrue(InputValidator.isValidEmotionOption(1));
        assertTrue(InputValidator.isValidEmotionOption(10));
        assertFalse(InputValidator.isValidEmotionOption(0));
        assertFalse(InputValidator.isValidEmotionOption(11));
    }
}
