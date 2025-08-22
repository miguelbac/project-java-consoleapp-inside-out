package org.factoriaf5.project_inside_out.repositories;

import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Movie;
import org.junit.jupiter.api.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvMovieRepositoryTest {

    private CsvMovieRepository repository;

    @BeforeEach
    void setUp() {
        // Usamos carpeta temporal para no modificar la real
        File folder = new File("exports");
        if (folder.exists()) {
            for (File f : folder.listFiles()) {
                f.delete();
            }
        }
        repository = new CsvMovieRepository();
    }

    @Test
    void saveAndFindAll_ShouldPersistMovie() {
        Movie movie = new Movie("tt001", "Inside Out", List.of("Animación", "Familiar"),
                Emotion.ALEGRIA, 2015, LocalDateTime.now());

        repository.save(movie);

        List<Movie> movies = repository.findAll();

        assertEquals(1, movies.size());
        assertEquals("Inside Out", movies.get(0).getTitle());
        assertEquals(Emotion.ALEGRIA, movies.get(0).getEmotion());
    }

    @Test
    void deleteById_ShouldRemoveMovie() {
        Movie movie = new Movie("tt002", "Toy Story", List.of("Animación"),
                Emotion.NOSTALGIA, 1995, LocalDateTime.now());
        repository.save(movie);

        repository.deleteById("tt002");

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void findByGenre_ShouldReturnCorrectMovies() {
        repository.save(new Movie("tt003", "Shrek", List.of("Comedia", "Aventura"),
                Emotion.ALEGRIA, 2001, LocalDateTime.now()));

        List<Movie> result = repository.findByGenre("comedia");

        assertEquals(1, result.size());
        assertEquals("Shrek", result.get(0).getTitle());
    }
}
