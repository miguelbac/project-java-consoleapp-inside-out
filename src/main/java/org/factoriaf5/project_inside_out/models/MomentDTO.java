package org.factoriaf5.project_inside_out.models;

public class MomentDTO {
    private String title;
    private String description;
    private int emotionOption;
    private String eventDate;
    private boolean isGood;

    public MomentDTO(String title, String description, int emotionOption, String eventDate, boolean isGood) {
        this.title = title;
        this.description = description;
        this.emotionOption = emotionOption;
        this.eventDate = eventDate;
        this.isGood = isGood;
    }

    public boolean isGood() {
        return isGood;
    }


    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getEmotionOption() { return emotionOption; }
    public void setEmotionOption(int emotionOption) { this.emotionOption = emotionOption; }

    public int getEmotion() { 
        return emotionOption; 
    }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }
}
