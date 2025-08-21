package org.factoriaf5.project_inside_out.repositories;

import org.factoriaf5.project_inside_out.models.Movie;

import java.util.List;

public interface MovieRepository {
    Movie save(Movie movie);
    List<Movie> findAll();
    void deleteById(String imdbId);
    List<Movie> findByGenre(String genre);
}
