package org.factoriaf5.project_inside_out.mappers;

import org.factoriaf5.project_inside_out.models.Movie;
import org.factoriaf5.project_inside_out.models.MovieDTO;

import java.time.LocalDateTime;

public class MovieMapper {

    public static Movie fromDTO(MovieDTO dto) {
        return new Movie(
                dto.getImdbId(),
                dto.getTitle(),
                dto.getGenres(),
                dto.getEmotion(),
                dto.getReleaseYear(),
                LocalDateTime.now()
        );
    }
}