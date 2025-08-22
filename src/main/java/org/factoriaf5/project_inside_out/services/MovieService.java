package org.factoriaf5.project_inside_out.services;

import org.factoriaf5.project_inside_out.models.Movie;
import org.factoriaf5.project_inside_out.models.MovieDTO;
import org.factoriaf5.project_inside_out.contracts.MovieRepository;
import org.factoriaf5.project_inside_out.mappers.MovieMapper;

import java.util.List;

public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public Movie addMovie(MovieDTO dto) {
        Movie movie = MovieMapper.fromDTO(dto);
        return repository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    public void deleteMovie(String imdbId) {
        repository.deleteById(imdbId);
    }

    public List<Movie> filterByGenre(String genre) {
        return repository.findByGenre(genre);
    }
}