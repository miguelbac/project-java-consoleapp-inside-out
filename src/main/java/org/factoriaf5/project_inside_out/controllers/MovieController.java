package org.factoriaf5.project_inside_out.controllers;

import org.factoriaf5.project_inside_out.models.MovieDTO;
import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.services.MovieService;
import org.factoriaf5.project_inside_out.utils.InputValidator;
import org.factoriaf5.project_inside_out.views.ConsoleMenu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieController {
    private final ConsoleMenu menu;
    private final MovieService service;

    public MovieController(ConsoleMenu menu, MovieService service) {
        this.menu = menu;
        this.service = service;
    }

    public void run() {
        while (true) {
            menu.showMovieMenu();
            int option = menu.readInt();

            switch (option) {
                case 1 -> addMovie();
                case 2 -> listMovies();
                case 3 -> deleteMovie();
                case 4 -> filterMovies();
                case 5 -> { return; }
                default -> menu.printError("Opción inválida.");
            }
        }
    }

    private void addMovie() {
        String imdbId = menu.readLine("IMDB Id: ");
        String title = menu.readLine("Título: ");
        String[] genresArray = menu.readLine("Géneros (separados por coma): ").split(",");
        
        // FIX: Limpiar espacios en géneros
        List<String> genres = Arrays.stream(genresArray)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        menu.printEmotionMenu();
        int emotionOption;
        do {
            emotionOption = menu.readInt("Seleccione una emoción: ");
            if (!InputValidator.isValidEmotionOption(emotionOption)) {
                menu.printError("Debe estar entre 1 y 10.");
            }
        } while (!InputValidator.isValidEmotionOption(emotionOption));

        Emotion emotion = Emotion.fromOption(emotionOption);

        int year;
        do {
            year = menu.readInt("Año de estreno: ");
            if (year <= 0) menu.printError("Año inválido");
        } while (year <= 0);

        MovieDTO dto = new MovieDTO(imdbId, title, genres, emotion, year);
        service.addMovie(dto);
        menu.printMessage("✅ Película añadida con éxito");
    }

    private void listMovies() {
        menu.printMovies(service.getAllMovies());
    }

    private void deleteMovie() {
        String imdbId = menu.readLine("Introduce el IMDB Id: ");
        service.deleteMovie(imdbId);
        menu.printMessage("✅ Película eliminada");
    }

    private void filterMovies() {
        String genre = menu.readLine("Introduce un género: ");
        menu.printMovies(service.filterByGenre(genre));
    }
}