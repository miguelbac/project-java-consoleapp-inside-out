package org.factoriaf5.project_inside_out.controllers;

import org.factoriaf5.project_inside_out.models.MomentDTO;
import org.factoriaf5.project_inside_out.models.Moment;
import org.factoriaf5.project_inside_out.services.MomentService;
import org.factoriaf5.project_inside_out.utils.InputValidator;
import org.factoriaf5.project_inside_out.utils.MomentExporter;
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
                case 4 -> filterMoments();
                case 5 -> exitApplication();
                case 6 -> exportMoments();

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

            boolean isGood = getValidMomentType();

            MomentDTO dto = new MomentDTO(title, description, emotionOption,
                    eventDate.format(InputValidator.getFormatter()), isGood);

            service.addMoment(dto);

            menu.printMessage("Momento vivido añadido correctamente.");
        } catch (Exception e) {
            menu.printError("Error al añadir el momento: " + e.getMessage());
        }
    }

    private boolean getValidMomentType() {
        int option;
        do {
            option = menu.readInt("¿Es un buen momento (1) o un mal momento (2)?: ");
            if (option != 1 && option != 2) {
                menu.printError("Debe introducir 1 (bueno) o 2 (malo).");
            }
        } while (option != 1 && option != 2);
        return option == 1;
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
        if ("test".equals(System.getProperty("environment"))) {
            throw new RuntimeException("System.exit() called");
        }
        System.exit(0);
    }

    private void filterMoments() {
        menu.printMessage("Filtrar por ...:");
        menu.printMessage("1. Emoción");
        menu.printMessage("2. Fecha");
        menu.printMessage("3. Buenos/Malos");

        int filterOption = menu.readInt("Ingrese una opción: ");

        switch (filterOption) {
            case 1 -> {
                menu.printEmotionMenu();
                int emotionOption = getValidEmotion();
                List<Moment> filteredByEmotion = service.getMomentsByEmotion(emotionOption);
                menu.printMoments(filteredByEmotion);
            }
            case 2 -> {
                int month;
                do {
                    month = menu.readInt("Ingrese el mes (1-12): ");
                    if (month < 1 || month > 12) {
                        menu.printError("Mes inválido. Debe estar entre 1 y 12.");
                    }
                } while (month < 1 || month > 12);

                int year;
                do {
                    year = menu.readInt("Ingrese el año (yyyy): ");
                    if (String.valueOf(year).length() != 4) {
                        menu.printError("Año inválido. Debe tener 4 dígitos.");
                    }
                } while (String.valueOf(year).length() != 4);

                List<Moment> filteredByMonthYear = service.getMomentsByMonthYear(month, year);
                menu.printMoments(filteredByMonthYear);
            }
            case 3 -> {
                boolean isGood = getValidMomentType();
                List<Moment> filtered = service.getMomentsByType(isGood);
                menu.printMoments(filtered);
            }
            default -> menu.printError("Opción inválida.");
        }
    }

    private void exportMoments() {
        String filename = menu.readLine("Ingrese el nombre del archivo CSV: ").trim();

        if (!filename.toLowerCase().endsWith(".csv")) {
            filename += ".csv";
        }

        List<Moment> allMoments = service.getAllMoments();
        MomentExporter.exportToCSV(allMoments, filename);
        menu.printMessage("Momentos exportados correctamente en: " + filename);
    }

}
