package org.factoriaf5.project_inside_out;

import org.factoriaf5.project_inside_out.views.ConsoleMenu;

public class App {
    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();

        while (true) {
            menu.showMainMenu();
            int option = menu.readInt();

            switch (option) {
                case 1 -> menu.printMessage("Has seleccionado: Añadir momento");
                case 2 -> menu.printMessage("Has seleccionado: Ver todos los momentos");
                case 3 -> menu.printMessage("Has seleccionado: Eliminar un momento");
                case 4 -> menu.printMessage("Has seleccionado: Filtrar los momentos");
                case 5 -> {
                    menu.printMessage("¡Hasta la próxima!");
                    System.exit(0);
                }
                default -> menu.printMessage("Opción inválida. Por favor, intenta de nuevo.");
            }
        }
    }
}
