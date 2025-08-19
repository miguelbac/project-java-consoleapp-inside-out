package org.factoriaf5.project_inside_out.controllers;

import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Moment;
import org.factoriaf5.project_inside_out.models.MomentDTO;
import org.factoriaf5.project_inside_out.services.MomentService;
import org.factoriaf5.project_inside_out.utils.InputValidator;
import org.factoriaf5.project_inside_out.views.ConsoleMenu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;


class MomentControllerTest {

    @Mock
    private ConsoleMenu menu;

    @Mock
    private MomentService service;

    private MomentController controller;
    private Moment testMoment;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        controller = new MomentController(menu, service);
        
        // Create a test moment using the first emotion (ALEGRIA = option 1)
        testMoment = new Moment(
            1, 
            "Test Title", 
            "Test Description", 
            Emotion.ALEGRIA, // This corresponds to emotion option 1
            LocalDate.of(2023, 12, 15),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testConstructor() {
        // Arrange & Act
        MomentController newController = new MomentController(menu, service);
        
        // Assert
        assertNotNull(newController);
    }

    @Test
    void testShowAllMoments() {
        // Arrange
        List<Moment> moments = Arrays.asList(testMoment);
        when(service.getAllMoments()).thenReturn(moments);
        when(menu.readInt()).thenReturn(2, 5); // show moments, then exit

        // Act
        try {
            controller.run();
        } catch (SystemExitException e) {
            // Expected
        }

        // Assert
        verify(service).getAllMoments();
        verify(menu).printMoments(moments);
    }

    @Test
    void testAddMomentSuccess() {
        // Arrange
        String title = "Test Title";
        String description = "Test Description";
        String dateInput = "15/12/2023";
        LocalDate eventDate = LocalDate.of(2023, 12, 15);
        int emotionOption = 5;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Mock menu interactions
        when(menu.readLine("Ingrese el título: ")).thenReturn(title);
        when(menu.readLine("Ingresa la fecha (dd/mm/yyyy): ")).thenReturn(dateInput);
        when(menu.readLine("Ingrese la descripción: ")).thenReturn(description);
        when(menu.readInt("Ingrese su opción: ")).thenReturn(emotionOption);
        when(menu.readInt()).thenReturn(1, 5); // add moment, then exit

        // Mock static methods
        try (MockedStatic<InputValidator> mockedValidator = mockStatic(InputValidator.class)) {
            mockedValidator.when(() -> InputValidator.parseDate(dateInput)).thenReturn(eventDate);
            mockedValidator.when(() -> InputValidator.isValidEmotionOption(emotionOption)).thenReturn(true);
            mockedValidator.when(InputValidator::getFormatter).thenReturn(formatter);

            when(service.addMoment(any(MomentDTO.class))).thenReturn(testMoment);

            // Act
            try {
                controller.run();
            } catch (SystemExitException e) {
                // Expected when option 5 is selected
            }

            // Assert
            ArgumentCaptor<MomentDTO> dtoCaptor = ArgumentCaptor.forClass(MomentDTO.class);
            verify(service).addMoment(dtoCaptor.capture());
            verify(menu).printMessage("Momento vivido añadido correctamente.");

            MomentDTO capturedDto = dtoCaptor.getValue();
            assertEquals(title, capturedDto.getTitle());
            assertEquals(description, capturedDto.getDescription());
            assertEquals(emotionOption, capturedDto.getEmotionOption());
            assertEquals("15/12/2023", capturedDto.getEventDate());
        }
    }

    @Test
    void testAddMomentWithInvalidDateThenValid() {
        // Arrange
        String title = "Test Title";
        String invalidDate = "invalid-date";
        String validDate = "15/12/2023";
        LocalDate eventDate = LocalDate.of(2023, 12, 15);
        String description = "Test Description";
        int emotionOption = 5;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        when(menu.readLine("Ingrese el título: ")).thenReturn(title);
        when(menu.readLine("Ingresa la fecha (dd/mm/yyyy): "))
                .thenReturn(invalidDate)
                .thenReturn(validDate);
        when(menu.readLine("Ingrese la descripción: ")).thenReturn(description);
        when(menu.readInt("Ingrese su opción: ")).thenReturn(emotionOption);
        when(menu.readInt()).thenReturn(1, 5);

        try (MockedStatic<InputValidator> mockedValidator = mockStatic(InputValidator.class)) {
            mockedValidator.when(() -> InputValidator.parseDate(invalidDate)).thenReturn(null);
            mockedValidator.when(() -> InputValidator.parseDate(validDate)).thenReturn(eventDate);
            mockedValidator.when(() -> InputValidator.isValidEmotionOption(emotionOption)).thenReturn(true);
            mockedValidator.when(InputValidator::getFormatter).thenReturn(formatter);

            when(service.addMoment(any(MomentDTO.class))).thenReturn(testMoment);

            // Act
            try {
                controller.run();
            } catch (SystemExitException e) {
                // Expected when option 5 is selected
            }

            // Assert
            verify(menu).printError("Formato de fecha inválido. Intenta de nuevo (dd/mm/yyyy).");
            verify(service).addMoment(any(MomentDTO.class));
            verify(menu).printMessage("Momento vivido añadido correctamente.");
        }
    }

    @Test
    void testAddMomentWithInvalidEmotionThenValid() {
        // Arrange
        String title = "Test Title";
        String dateInput = "15/12/2023";
        LocalDate eventDate = LocalDate.of(2023, 12, 15);
        String description = "Test Description";
        int invalidEmotion = 15;
        int validEmotion = 5;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        when(menu.readLine("Ingrese el título: ")).thenReturn(title);
        when(menu.readLine("Ingresa la fecha (dd/mm/yyyy): ")).thenReturn(dateInput);
        when(menu.readLine("Ingrese la descripción: ")).thenReturn(description);
        when(menu.readInt("Ingrese su opción: "))
                .thenReturn(invalidEmotion)
                .thenReturn(validEmotion);
        when(menu.readInt()).thenReturn(1, 5);

        try (MockedStatic<InputValidator> mockedValidator = mockStatic(InputValidator.class)) {
            mockedValidator.when(() -> InputValidator.parseDate(dateInput)).thenReturn(eventDate);
            mockedValidator.when(() -> InputValidator.isValidEmotionOption(invalidEmotion)).thenReturn(false);
            mockedValidator.when(() -> InputValidator.isValidEmotionOption(validEmotion)).thenReturn(true);
            mockedValidator.when(InputValidator::getFormatter).thenReturn(formatter);

            when(service.addMoment(any(MomentDTO.class))).thenReturn(testMoment);

            // Act
            try {
                controller.run();
            } catch (SystemExitException e) {
                // Expected when option 5 is selected
            }

            // Assert
            verify(menu).printError("Opción inválida. Introduzca un número entre 1 y 10.");
            verify(service).addMoment(any(MomentDTO.class));
        }
    }

    @Test
    void testAddMomentWithException() {
        // Arrange
        when(menu.readLine("Ingrese el título: ")).thenThrow(new RuntimeException("Test exception"));
        when(menu.readInt()).thenReturn(1, 5);

        // Act
        try {
            controller.run();
        } catch (SystemExitException e) {
            // Expected when option 5 is selected
        }

        // Assert
        verify(menu).printError(contains("Error al añadir el momento:"));
        verify(service, never()).addMoment(any(MomentDTO.class));
    }

    @Test
    void testDeleteMomentSuccess() {
        // Arrange
        int momentId = 1;
        when(service.getAllMoments()).thenReturn(Arrays.asList(testMoment));
        when(menu.readInt("Ingresa el identificador del momento: ")).thenReturn(momentId);
        when(menu.readInt()).thenReturn(3, 5); // delete, then exit

        // Act
        try {
            controller.run();
        } catch (SystemExitException e) {
            // Expected when option 5 is selected
        }

        // Assert
        verify(service).deleteMoment(momentId);
        verify(menu).printMessage("Momento vivido eliminado correctamente.");
    }

    @Test
    void testDeleteMomentNotFound() {
        // Arrange
        int momentId = 999;
        when(service.getAllMoments()).thenReturn(Collections.emptyList());
        when(menu.readInt("Ingresa el identificador del momento: ")).thenReturn(momentId);
        when(menu.readInt()).thenReturn(3, 5); // delete, then exit

        // Act
        try {
            controller.run();
        } catch (SystemExitException e) {
            // Expected when option 5 is selected
        }

        // Assert
        verify(service, never()).deleteMoment(momentId);
        verify(menu).printError("No existe ningún momento con el ID: " + momentId);
    }

    @Test
    void testDeleteMomentWithException() {
        // Arrange
        when(service.getAllMoments()).thenThrow(new RuntimeException("Test exception"));
        when(menu.readInt()).thenReturn(3, 5);

        // Act
        try {
            controller.run();
        } catch (SystemExitException e) {
            // Expected when option 5 is selected
        }

        // Assert
        verify(menu).printError(contains("Error al eliminar el momento:"));
    }

    @Test
    void testFilterMomentsByEmotion() {
        // Arrange
        int emotionOption = 3;
        List<Moment> filteredMoments = Arrays.asList(testMoment);
        when(service.getMomentsByEmotion(emotionOption)).thenReturn(filteredMoments);
        when(menu.readInt("Ingrese una opción: ")).thenReturn(1); // Filter by emotion
        when(menu.readInt("Ingrese su opción: ")).thenReturn(emotionOption);
        when(menu.readInt()).thenReturn(4, 5); // filter, then exit

        try (MockedStatic<InputValidator> mockedValidator = mockStatic(InputValidator.class)) {
            mockedValidator.when(() -> InputValidator.isValidEmotionOption(emotionOption)).thenReturn(true);

            // Act
            try {
                controller.run();
            } catch (SystemExitException e) {
                // Expected when option 5 is selected
            }

            // Assert
            verify(service).getMomentsByEmotion(emotionOption);
            verify(menu).printMoments(filteredMoments);
        }
    }

    @Test
    void testFilterMomentsByDate() {
        // Arrange
        int month = 12;
        int year = 2023;
        List<Moment> filteredMoments = Arrays.asList(testMoment);
        when(service.getMomentsByMonthYear(month, year)).thenReturn(filteredMoments);
        when(menu.readInt("Ingrese una opción: ")).thenReturn(2); // Filter by date
        when(menu.readInt("Ingrese el mes (1-12): ")).thenReturn(month);
        when(menu.readInt("Ingrese el año (yyyy): ")).thenReturn(year);
        when(menu.readInt()).thenReturn(4, 5); // filter, then exit

        // Act
        try {
            controller.run();
        } catch (SystemExitException e) {
            // Expected when option 5 is selected
        }

        // Assert
        verify(service).getMomentsByMonthYear(month, year);
        verify(menu).printMoments(filteredMoments);
    }

    @Test
    void testFilterMomentsInvalidMonth() {
        // Arrange
        int invalidMonth = 13;
        int validMonth = 12;
        int year = 2023;
        List<Moment> filteredMoments = Arrays.asList(testMoment);
        when(service.getMomentsByMonthYear(validMonth, year)).thenReturn(filteredMoments);
        when(menu.readInt("Ingrese una opción: ")).thenReturn(2); // Filter by date
        when(menu.readInt("Ingrese el mes (1-12): ")).thenReturn(invalidMonth, validMonth);
        when(menu.readInt("Ingrese el año (yyyy): ")).thenReturn(year);
        when(menu.readInt()).thenReturn(4, 5); // filter, then exit

        // Act
        try {
            controller.run();
        } catch (SystemExitException e) {
            // Expected when option 5 is selected
        }

        // Assert
        verify(menu).printError("Mes inválido. Debe estar entre 1 y 12.");
        verify(service).getMomentsByMonthYear(validMonth, year);
    }

    @Test
    void testFilterMomentsInvalidYear() {
        // Arrange
        int month = 12;
        int invalidYear = 23; // 2-digit year
        int validYear = 2023;
        List<Moment> filteredMoments = Arrays.asList(testMoment);
        when(service.getMomentsByMonthYear(month, validYear)).thenReturn(filteredMoments);
        when(menu.readInt("Ingrese una opción: ")).thenReturn(2); // Filter by date
        when(menu.readInt("Ingrese el mes (1-12): ")).thenReturn(month);
        when(menu.readInt("Ingrese el año (yyyy): ")).thenReturn(invalidYear, validYear);
        when(menu.readInt()).thenReturn(4, 5); // filter, then exit

        // Act
        try {
            controller.run();
        } catch (SystemExitException e) {
            // Expected when option 5 is selected
        }

        // Assert
        verify(menu).printError("Año inválido. Debe tener 4 dígitos.");
        verify(service).getMomentsByMonthYear(month, validYear);
    }

    @Test
    void testFilterMomentsInvalidOption() {
        // Arrange
        when(menu.readInt("Ingrese una opción: ")).thenReturn(999); // Invalid filter option
        when(menu.readInt()).thenReturn(4, 5); // filter, then exit

        // Act
        try {
            controller.run();
        } catch (SystemExitException e) {
            // Expected when option 5 is selected
        }

        // Assert
        verify(menu).printError("Opción inválida.");
        verify(service, never()).getMomentsByEmotion(anyInt());
        verify(service, never()).getMomentsByMonthYear(anyInt(), anyInt());
    }

    @Test
    void testInvalidMainMenuOption() {
        // Arrange
        when(menu.readInt()).thenReturn(999, 5); // Invalid option, then exit

        // Act
        try {
            controller.run();
        } catch (SystemExitException e) {
            // Expected when option 5 is selected
        }

        // Assert
        verify(menu).printMessage("Opción inválida. Por favor, intenta de nuevo.");
    }

    @Test
    void testExitApplication() {
        // Arrange
        when(menu.readInt()).thenReturn(5); // Exit option

        // Act & Assert
        assertThrows(SystemExitException.class, () -> controller.run());
        verify(menu).printMessage("¡Hasta la próxima!");
    }

    // Custom exception to handle System.exit() in tests
    // You'll need to modify your exitApplication method to throw this exception
    // instead of calling System.exit(0) when in test mode
    static class SystemExitException extends RuntimeException {
        public SystemExitException() {
            super("System.exit() called");
        }
    }
}