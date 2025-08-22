package org.factoriaf5.project_inside_out.services;

import org.factoriaf5.project_inside_out.models.*;
import org.factoriaf5.project_inside_out.repositories.CsvMovieRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    private MovieService service;

    @BeforeEach
    void setUp() {
        // Limpiamos carpeta exports antes de cada test
        File folder = new File("exports");
        if (folder.exists()) {
            for (File f : folder.listFiles()) {
                f.delete();
            }
        }
        service = new MovieService(new CsvMovieRepository());
    }

    @Test
    void addMovie_ShouldSaveFromDTO() {
        MovieDTO dto = new MovieDTO("tt010", "Matrix", List.of("Sci-Fi"), Emotion.MIEDO, 1999);

        Movie movie = service.addMovie(dto);

        assertNotNull(movie);
        assertEquals("Matrix", movie.getTitle());
    }

    @Test
    void getAllMovies_ShouldReturnAllSaved() {
        service.addMovie(new MovieDTO("tt001", "Inception", List.of("Sci-Fi"), Emotion.ANSIEDAD, 2010));
        service.addMovie(new MovieDTO("tt002", "Shrek", List.of("Animación"), Emotion.ALEGRIA, 2001));

        List<Movie> movies = service.getAllMovies();

        assertEquals(2, movies.size());
    }

    @Test
    void deleteMovie_ShouldRemoveMovieById() {
        service.addMovie(new MovieDTO("tt003", "Avatar", List.of("Aventura"), Emotion.ALEGRIA, 2009));

        service.deleteMovie("tt003");

        assertTrue(service.getAllMovies().isEmpty());
    }

    @Test
    void filterByGenre_ShouldReturnCorrectMovies() {
        service.addMovie(new MovieDTO("tt004", "Titanic", List.of("Drama", "Romance"), Emotion.TRISTEZA, 1997));
        service.addMovie(new MovieDTO("tt005", "Up", List.of("Animación", "Aventura"), Emotion.NOSTALGIA, 2009));

        List<Movie> dramas = service.filterByGenre("drama");

        assertEquals(1, dramas.size());
        assertEquals("Titanic", dramas.get(0).getTitle());
    }
}
