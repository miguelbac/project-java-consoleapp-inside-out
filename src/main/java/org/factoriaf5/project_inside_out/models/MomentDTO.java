package org.factoriaf5.project_inside_out.models;

public class MomentDTO {
    private String title;
    private String description;
    private int emotionOption;
    private String eventDate;

    public MomentDTO() {}

    public MomentDTO(String title, String description, int emotionOption, String eventDate) {
        this.title = title;
        this.description = description;
        this.emotionOption = emotionOption;
        this.eventDate = eventDate;
    }

    // Getters y setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getEmotionOption() { return emotionOption; }
    public void setEmotionOption(int emotionOption) { this.emotionOption = emotionOption; }

    // Alias para compatibilidad con tests que usen getEmotion()
    public int getEmotion() { 
        return emotionOption; 
    }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }
}
