package org.factoriaf5.project_inside_out.controllers;

import org.factoriaf5.project_inside_out.services.MomentService;
import org.factoriaf5.project_inside_out.services.MovieService;
import org.factoriaf5.project_inside_out.views.ConsoleMenu;

public class AppController {

    private final ConsoleMenu menu;
    private final MomentController momentController;
    private final MovieController movieController;

    public AppController(ConsoleMenu menu, MomentService momentService, MovieService movieService) {
        this.menu = menu;
        this.momentController = new MomentController(menu, momentService);
        this.movieController = new MovieController(menu, movieService);
    }

    public void run() {
        while (true) {
            menu.showMainMenu();
            int option = menu.readInt();

            switch (option) {
                case 1 -> momentController.run();
                case 2 -> movieController.run();
                case 3 -> {
                    menu.printMessage("¡Hasta la próxima!");
                    return;
                }
                default -> menu.printError("Opción inválida.");
            }
        }
    }
}
