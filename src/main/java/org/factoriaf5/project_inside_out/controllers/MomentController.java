package org.factoriaf5.project_inside_out.controllers;

import org.factoriaf5.project_inside_out.models.MomentDTO;
import org.factoriaf5.project_inside_out.models.Moment;
import org.factoriaf5.project_inside_out.services.MomentService;
import org.factoriaf5.project_inside_out.utils.InputValidator;
import org.factoriaf5.project_inside_out.views.ConsoleMenu;
import java.time.LocalDate;
import java.util.List;

public class MomentController {
    private final ConsoleMenu menu;
    private final MomentService service;

    public MomentController(ConsoleMenu menu, MomentService service) {
        this.menu = menu;
        this.service = service;
    }

    public void run() {
        while (true) {
            menu.showMainMenu();
            int option = menu.readInt();

            switch (option) {
                case 1 -> addMoment();
                case 2 -> showAllMoments();
                case 3 -> deleteMoment();
                case 4 -> menu.printMessage("Función no implementada aún.");
                case 5 -> exitApplication();
                default -> menu.printMessage("Opción inválida. Por favor, intenta de nuevo.");
            }
        }
    }

    private void addMoment() {
        try {
            String title = menu.readLine("Ingrese el título: ");
            LocalDate eventDate = getValidDate();
            String description = menu.readLine("Ingrese la descripción: ");
            int emotionOption = getValidEmotion();

            MomentDTO dto = new MomentDTO(title, description, emotionOption,
                    eventDate.format(InputValidator.getFormatter()));
            service.addMoment(dto);

            menu.printMessage("Momento vivido añadido correctamente.");
        } catch (Exception e) {
            menu.printError("Error al añadir el momento: " + e.getMessage());
        }
    }

    private void deleteMoment() {
        try {
            int id = menu.readInt("Ingresa el identificador del momento: ");

            boolean exists = service.getAllMoments().stream()
                    .anyMatch(moment -> moment.getId() == id);

            if (!exists) {
                menu.printError("No existe ningún momento con el ID: " + id);
                return;
            }

            service.deleteMoment(id);
            menu.printMessage("Momento vivido eliminado correctamente.");
        } catch (Exception e) {
            menu.printError("Error al eliminar el momento: " + e.getMessage());
        }
    }

    private LocalDate getValidDate() {
        LocalDate eventDate;
        do {
            String dateInput = menu.readLine("Ingresa la fecha (dd/mm/yyyy): ");
            eventDate = InputValidator.parseDate(dateInput);
            if (eventDate == null) {
                menu.printError("Formato de fecha inválido. Intenta de nuevo (dd/mm/yyyy).");
            }
        } while (eventDate == null);
        return eventDate;
    }

    private int getValidEmotion() {
        menu.printEmotionMenu();
        int emotionOption;
        do {
            emotionOption = menu.readInt("Ingrese su opción: ");
            if (!InputValidator.isValidEmotionOption(emotionOption)) {
                menu.printError("Opción inválida. Introduzca un número entre 1 y 10.");
            }
        } while (!InputValidator.isValidEmotionOption(emotionOption));
        return emotionOption;
    }

    private void showAllMoments() {
        List<Moment> moments = service.getAllMoments();
        menu.printMoments(moments);
    }

    private void exitApplication() {
        menu.printMessage("¡Hasta la próxima!");
        System.exit(0);
    }
}
