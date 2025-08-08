package org.factoriaf5.project_inside_out.views;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import org.factoriaf5.project_inside_out.models.Moment;

public class ConsoleMenu {
    private static final String MENU_TEXT = """
            My Diario:
            1. Añadir momento
            2. Ver todos los momentos
            3. Eliminar un momento
            4. Filtrar los momentos
            5. Salir
            Seleccione una opción:
            """;

    private static final String EMOTION_MENU = """
            Selecciona una emoción:
            1. Alegría
            2. Tristeza
            3. Ira
            4. Asco
            5. Miedo
            6. Ansiedad
            7. Envidia
            8. Vergüenza
            9. Aburrimiento
            10. Nostalgia
            """;

    private final Scanner scanner = new Scanner(System.in);

    public void showMainMenu() {
        System.out.println(MENU_TEXT);
    }

    public void printEmotionMenu() {
        System.out.println(EMOTION_MENU);
    }

    public int readInt() {
        return readInt("");
    }

    public int readInt(String prompt) {
        if (!prompt.isEmpty()) {
            System.out.print(prompt);
        }
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String error) {
        System.err.println("Error: " + error);
    }

    public void printMoments(List<Moment> moments) {
        if (moments.isEmpty()) {
            System.out.println("No hay momentos guardados.");
            return;
        }

        System.out.println("Lista de momentos vividos:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Moment moment : moments) {
            System.out.printf("%d. Ocurrió el: %s. Título: %s. Descripción: %s. Emoción: %s%n",
                    moment.getId(),
                    moment.getEventDate().format(formatter),
                    moment.getTitle(),
                    moment.getDescription(),
                    moment.getEmotion()
            );
        }
    }
}