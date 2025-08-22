package org.factoriaf5.project_inside_out.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Moment {
    private int id;
    private String title;
    private String description;
    private Emotion emotion;
    private LocalDate eventDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isGood;

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
        this.title = title;
        this.description = description;
        this.emotion = emotion;
        this.eventDate = eventDate;
        this.isGood = isGood;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Emotion getEmotion() { return emotion; }
    public LocalDate getEventDate() { return eventDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public boolean isGood() { return isGood; }
    
    public void setId(int id) { this.id = id; }
}