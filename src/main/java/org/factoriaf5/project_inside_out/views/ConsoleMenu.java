package org.factoriaf5.project_inside_out.views;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.factoriaf5.project_inside_out.models.Moment;
import org.factoriaf5.project_inside_out.models.Movie;

public class ConsoleMenu {

    private static final String MAIN_MENU = """
            My Diario:
            1. Gestionar momentos
            2. Gestionar películas
            3. Salir
            Seleccione una opción:
            """;

    private static final String MOMENT_MENU = """
            --- Momentos ---
            1. Añadir momento
            2. Ver todos los momentos
            3. Eliminar un momento
            4. Filtrar los momentos
            5. Exportar momentos a CSV
            6. Volver
            Seleccione una opción:
            """;

    private static final String MOVIE_MENU = """
            --- Películas ---
            1. Añadir película
            2. Ver todas las películas
            3. Eliminar una película
            4. Filtrar películas por género
            5. Volver
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

    // NUEVO: Menú de filtros
    private static final String FILTER_MENU = """
            --- Filtrar Momentos ---
            1. Por emoción
            2. Por fecha
            3. Por tipo (bueno/malo)
            4. Volver
            Seleccione una opción:
            """;

    private final Scanner scanner = new Scanner(System.in);

    // ===== MENÚS =====
    public void showMainMenu() {
        System.out.println(MAIN_MENU);
    }

    public void showMomentMenu() {
        System.out.println(MOMENT_MENU);
    }

    public void showMovieMenu() {
        System.out.println(MOVIE_MENU);
    }

    public void printEmotionMenu() {
        System.out.println(EMOTION_MENU);
    }

    // NUEVO: Menú de filtros
    public void showFilterMenu() {
        System.out.println(FILTER_MENU);
    }

    // ===== INPUTS =====
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

    // ===== SALIDAS =====
    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String error) {
        System.err.println("Error: " + error);
    }

    // ===== IMPRESIÓN DE DATOS =====
    public void printMoments(List<Moment> moments) {
        if (moments.isEmpty()) {
            System.out.println("No hay momentos guardados.");
            return;
        }

        System.out.println("Lista de momentos vividos:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Moment moment : moments) {
            System.out.printf("%d. Ocurrió el: %s. Título: %s. Descripción: %s. Emoción: %s. Tipo: %s%n",
                    moment.getId(),
                    moment.getEventDate().format(formatter),
                    moment.getTitle(),
                    moment.getDescription(),
                    moment.getEmotion().getDisplayName(),
                    moment.isGood() ? "BUENO" : "MALO");
        }
    }

    public void printMovies(List<Movie> movies) {
        if (movies.isEmpty()) {
            System.out.println("No hay películas guardadas.");
            return;
        }
        System.out.println("Lista de películas:");
        for (Movie movie : movies) {
            System.out.printf("IMDB: %s | Título: %s | Género(s): %s | Emoción: %s | Año estreno: %d | Creado en: %s%n",
                    movie.getImdbId(),
                    movie.getTitle(),
                    String.join(", ", movie.getGenres()),
                    movie.getEmotion().getDisplayName(),
                    movie.getReleaseYear(),
                    movie.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            );
        }
    }
}
