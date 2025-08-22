package org.factoriaf5.project_inside_out;

import org.factoriaf5.project_inside_out.contracts.MomentRepository;
import org.factoriaf5.project_inside_out.contracts.MovieRepository;
import org.factoriaf5.project_inside_out.controllers.AppController;
import org.factoriaf5.project_inside_out.repositories.CsvMovieRepository;
import org.factoriaf5.project_inside_out.repositories.InMemoryMomentRepository;
import org.factoriaf5.project_inside_out.db.MomentDB;
import org.factoriaf5.project_inside_out.services.MovieService;
import org.factoriaf5.project_inside_out.services.MomentService;
import org.factoriaf5.project_inside_out.views.ConsoleMenu;

public class App {
    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();

        // ---- Configuración Momentos ----
        MomentDB momentDB = new MomentDB();
        MomentRepository momentRepository = new InMemoryMomentRepository(momentDB);
        MomentService momentService = new MomentService(momentRepository);

        // ---- Configuración Películas ----
        MovieRepository movieRepository = new CsvMovieRepository();
        MovieService movieService = new MovieService(movieRepository);

        // ---- Arrancar aplicación principal ----
        AppController appController = new AppController(menu, momentService, movieService);
        appController.run();
    }
}
