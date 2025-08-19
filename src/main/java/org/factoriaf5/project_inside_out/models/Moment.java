package org.factoriaf5.project_inside_out.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Moment {
    private final int id;
    private final String title;
    private final String description;
    private final Emotion emotion;
    private final LocalDate eventDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean isGood; // <-- nuevo campo

    public Moment(int id, String title, String description, Emotion emotion, LocalDate eventDate,
                  LocalDateTime createdAt, LocalDateTime updatedAt, boolean isGood) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.emotion = emotion;
        this.eventDate = eventDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isGood = isGood;
    }

    public Moment(String title, String description, Emotion emotion, LocalDate eventDate, boolean isGood) {
        this(0, title, description, emotion, eventDate, null, null, isGood);
    }

    public boolean isGood() {
        return isGood;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}