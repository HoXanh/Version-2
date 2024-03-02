package com.fitfusion.myapplication.Model;

public class FitnessPlan {
    //
    private String title;
    private String level;
    private String duration;
    private String image; // URL to the image
    private String description;

    public FitnessPlan(){}


    public FitnessPlan(String title, String level, String duration, String image, String description) {
        this.title = title;
        this.level = level;
        this.duration = duration;
        this.image = image;
        this.description = description;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}