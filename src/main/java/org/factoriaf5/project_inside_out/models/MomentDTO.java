package org.factoriaf5.project_inside_out.models;

import java.time.LocalDate;

public class MomentDTO {
    private String title;
    private String description;
    private Emotion emotion;
    private LocalDate eventDate;
    private boolean isGood;

    public MomentDTO(String title, String description, Emotion emotion, LocalDate eventDate, boolean isGood) {
        this.title = title;
        this.description = description;
        this.emotion = emotion;
        this.eventDate = eventDate;
        this.isGood = isGood;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Emotion getEmotion() { return emotion; }
    public LocalDate getEventDate() { return eventDate; }
    public boolean isGood() { return isGood; }
}