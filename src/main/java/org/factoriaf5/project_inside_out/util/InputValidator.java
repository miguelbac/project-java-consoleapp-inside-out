package org.factoriaf5.project_inside_out.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate parseDate(String input) {
        try {
            return LocalDate.parse(input, FORMATTER);
        } catch (DateTimeParseException e) {
            return null; 
        }
    }

    public static boolean isValidEmotionOption(int option) {
        return option >= 1 && option <= 10;
    }
}