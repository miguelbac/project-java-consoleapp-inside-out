package org.factoriaf5.project_inside_out.controllers;

import org.factoriaf5.project_inside_out.models.MomentDTO;
import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.services.MomentService;
import org.factoriaf5.project_inside_out.utils.InputValidator;
import org.factoriaf5.project_inside_out.views.ConsoleMenu;

import java.time.LocalDate;

public class MomentController {
    private final ConsoleMenu menu;
    private final MomentService service;

    public MomentController(ConsoleMenu menu, MomentService service) {
        this.menu = menu;
        this.service = service;
    }

    public void run() {
        while (true) {
            menu.showMomentMenu();
            int option = menu.readInt();
            switch (option) {
                case 1 -> addMoment();
                case 2 -> showAllMoments();
                case 3 -> deleteMoment();
                case 4 -> filterMoments();
                case 5 -> exportMoments();
                case 6 -> { return; }
                default -> menu.printError("Opción inválida.");
            }
        }
    }

    private void addMoment() {
        String title = menu.readLine("Ingrese el título: ");
        LocalDate date = getValidDate();
        String description = menu.readLine("Ingrese la descripción: ");
        int emotionOption = getValidEmotion();
        boolean isGood = getValidMomentType();

        Emotion emotion = Emotion.fromOption(emotionOption);

        MomentDTO dto = new MomentDTO(title, description, emotion, date, isGood);
        service.addMoment(dto);
        menu.printMessage("Momento añadido correctamente.");
    }

    private LocalDate getValidDate() {
        LocalDate date;
        do {
            String input = menu.readLine("Ingrese la fecha (dd/mm/yyyy): ");
            date = InputValidator.parseDate(input);
            if (date == null) menu.printError("Formato inválido.");
        } while (date == null);
        return date;
    }

    private int getValidEmotion() {
        menu.printEmotionMenu();
        int option;
        do {
            option = menu.readInt("Seleccione una emoción: ");
            if (!InputValidator.isValidEmotionOption(option)) menu.printError("Debe estar entre 1 y 10.");
        } while (!InputValidator.isValidEmotionOption(option));
        return option;
    }

    private boolean getValidMomentType() {
        int option;
        do {
            option = menu.readInt("¿Es un buen momento (1) o un mal momento (2)?: ");
            if (option != 1 && option != 2) menu.printError("Debe ser 1 o 2.");
        } while (option != 1 && option != 2);
        return option == 1;
    }

    private void showAllMoments() {
        menu.printMoments(service.getAllMoments());
    }

    private void deleteMoment() {
        int id = menu.readInt("Ingresa el ID del momento: ");
        service.deleteMoment(id);
        menu.printMessage("Momento eliminado correctamente.");
    }

    private void filterMoments() {
        menu.showFilterMenu();
        int filterOption = menu.readInt();
        
        switch (filterOption) {
            case 1 -> filterByEmotion();
            case 2 -> filterByDate();
            case 3 -> filterByType();
            case 4 -> { return; }
            default -> menu.printError("Opción inválida.");
        }
    }
    
    private void filterByEmotion() {
        int emotionOption = getValidEmotion();
        Emotion emotion = Emotion.fromOption(emotionOption);
        menu.printMoments(service.filterByEmotion(emotion));
    }
    
    private void filterByDate() {
        LocalDate date = getValidDate();
        menu.printMoments(service.filterByDate(date));
    }
    
    private void filterByType() {
        boolean isGood = getValidMomentType();
        menu.printMoments(service.filterByType(isGood));
    }

    private void exportMoments() {
        String filename = menu.readLine("Nombre del archivo CSV: ");
        if (!filename.toLowerCase().endsWith(".csv")) filename += ".csv";
        
        try {
            service.exportToCSV(filename);
            menu.printMessage("Exportado correctamente en: exports/" + filename);
        } catch (Exception e) {
            menu.printError("Error al exportar: " + e.getMessage());
        }
    }
}   