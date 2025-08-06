package org.factoriaf5.project_inside_out.views;

import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);

    public void showMainMenu() {
        System.out.println("""
                My Diario:
                1. Añadir momento
                2. Ver todos los momentos
                3. Eliminar un momento
                4. Filtrar los momentos
                5. Salir
                Seleccione una opción: 
                """);
    }

    public int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String error) {
        System.err.println("Error: " + error);
    }
}