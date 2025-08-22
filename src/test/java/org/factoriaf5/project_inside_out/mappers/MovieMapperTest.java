package org.factoriaf5.project_inside_out.mappers;

import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Movie;
import org.factoriaf5.project_inside_out.models.MovieDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MovieMapperTest {

    private MovieDTO movieDTO;
    private final List<String> genres = Arrays.asList("Action", "Adventure");
    private final Emotion emotion = Emotion.ALEGRIA;

    @BeforeEach
    void setUp() {
        movieDTO = new MovieDTO(
            "tt1234567",
            "Test Movie",
            genres,
            emotion,
            2023
        );
    }

    @Test
    void testFromDTO_ShouldReturnMovieWithCorrectImdbId() {
        // Act
        Movie result = MovieMapper.fromDTO(movieDTO);

        // Assert
        assertEquals("tt1234567", result.getImdbId());
    }

    @Test
    void testFromDTO_ShouldReturnMovieWithCorrectTitle() {
        // Act
        Movie result = MovieMapper.fromDTO(movieDTO);

        // Assert
        assertEquals("Test Movie", result.getTitle());
    }

    @Test
    void testFromDTO_ShouldReturnMovieWithCorrectGenres() {
        // Act
        Movie result = MovieMapper.fromDTO(movieDTO);

        // Assert
        assertEquals(genres, result.getGenres());
        assertTrue(result.getGenres().contains("Action"));
        assertTrue(result.getGenres().contains("Adventure"));
    }

    @Test
    void testFromDTO_ShouldReturnMovieWithCorrectEmotion() {
        // Act
        Movie result = MovieMapper.fromDTO(movieDTO);

        // Assert
        assertEquals(emotion, result.getEmotion());
    }

    @Test
    void testFromDTO_ShouldReturnMovieWithCorrectReleaseYear() {
        // Act
        Movie result = MovieMapper.fromDTO(movieDTO);

        // Assert
        assertEquals(2023, result.getReleaseYear());
    }

    @Test
    void testFromDTO_ShouldSetCurrentDateTime() {
        // Arrange
        LocalDateTime beforeTest = LocalDateTime.now();

        // Act
        Movie result = MovieMapper.fromDTO(movieDTO);
        LocalDateTime afterTest = LocalDateTime.now();

        // Assert
        assertNotNull(result.getCreatedAt());
        assertTrue(result.getCreatedAt().isAfter(beforeTest) || result.getCreatedAt().equals(beforeTest));
        assertTrue(result.getCreatedAt().isBefore(afterTest) || result.getCreatedAt().equals(afterTest));
    }

    @Test
    void testFromDTO_WithNullDTO_ShouldThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            MovieMapper.fromDTO(null);
        });
    }

    @Test
    void testFromDTO_WithEmptyFields_ShouldHandleCorrectly() {
        // Arrange
        MovieDTO emptyDTO = new MovieDTO("", "", Arrays.asList(), null, 0);

        // Act
        Movie result = MovieMapper.fromDTO(emptyDTO);

        // Assert
        assertEquals("", result.getImdbId());
        assertEquals("", result.getTitle());
        assertTrue(result.getGenres().isEmpty());
        assertNull(result.getEmotion());
        assertEquals(0, result.getReleaseYear());
        assertNotNull(result.getCreatedAt());
    }
}