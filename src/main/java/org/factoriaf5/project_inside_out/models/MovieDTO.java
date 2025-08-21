package org.factoriaf5.project_inside_out.models;

import java.util.List;

public class MovieDTO {
    private String imdbId;
    private String title;
    private List<String> genres;
    private Emotion emotion;
    private int releaseYear;

    public MovieDTO(String imdbId, String title, List<String> genres, Emotion emotion, int releaseYear) {
        this.imdbId = imdbId;
        this.title = title;
        this.genres = genres;
        this.emotion = emotion;
        this.releaseYear = releaseYear;
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
}
