package org.factoriaf5.project_inside_out.services;

import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Movie;
import org.factoriaf5.project_inside_out.models.MovieDTO;
import org.factoriaf5.project_inside_out.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    private MovieRepository repository;
    private MovieService movieService;

    private MovieDTO movieDTO;
    private Movie movie;

    @BeforeEach
    void setUp() {
        repository = mock(MovieRepository.class);
        movieService = new MovieService(repository);

        movieDTO = new MovieDTO(
            "tt1234567",
            "Test Movie",
            Arrays.asList("Action", "Adventure"),
            Emotion.ALEGRIA,
            2023
        );

        movie = new Movie(
            "tt1234567",
            "Test Movie",
            Arrays.asList("Action", "Adventure"),
            Emotion.ALEGRIA,
            2023,
            null
        );
    }

    @Test
    void testAddMovie_ShouldCallRepositorySave() {
        // Arrange
        when(repository.save(any(Movie.class))).thenReturn(movie);

        // Act
        Movie result = movieService.addMovie(movieDTO);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).save(any(Movie.class));
    }

    @Test
    void testGetAllMovies_ShouldReturnAllMovies() {
        // Arrange
        List<Movie> expectedMovies = Arrays.asList(movie);
        when(repository.findAll()).thenReturn(expectedMovies);

        // Act
        List<Movie> result = movieService.getAllMovies();

        // Assert
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testDeleteMovie_ShouldCallRepositoryDelete() {
        // Act
        movieService.deleteMovie("tt1234567");

        // Assert
        verify(repository, times(1)).deleteById("tt1234567");
    }

    @Test
    void testFilterByGenre_ShouldReturnFilteredMovies() {
        // Arrange
        List<Movie> expectedMovies = Arrays.asList(movie);
        when(repository.findByGenre("Action")).thenReturn(expectedMovies);

        // Act
        List<Movie> result = movieService.filterByGenre("Action");

        // Assert
        assertEquals(1, result.size());
        verify(repository, times(1)).findByGenre("Action");
    }
}