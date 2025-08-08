package org.factoriaf5.project_inside_out.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final int MIN_EMOTION_OPTION = 1;
    private static final int MAX_EMOTION_OPTION = 10;

    public static LocalDate parseDate(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        
        try {
            return LocalDate.parse(input.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            return null; 
        }
    }

    public static boolean isValidEmotionOption(int option) {
        return option >= MIN_EMOTION_OPTION && option <= MAX_EMOTION_OPTION;
    }

    public static DateTimeFormatter getFormatter() {
        return FORMATTER;
    }
}
