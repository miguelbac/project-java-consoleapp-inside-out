package org.factoriaf5.project_inside_out.models;

import java.time.LocalDateTime;
import java.util.List;

public class Movie {
    private String imdbId;
    private String title;
    private List<String> genres;
    private Emotion emotion;
    private int releaseYear;
    private LocalDateTime createdAt;

    public Movie(String imdbId, String title, List<String> genres, Emotion emotion, int releaseYear, LocalDateTime createdAt) {
        this.imdbId = imdbId;
        this.title = title;
        this.genres = genres;
        this.emotion = emotion;
        this.releaseYear = releaseYear;
        this.createdAt = createdAt;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
