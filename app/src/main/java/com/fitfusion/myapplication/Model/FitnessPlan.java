package com.fitfusion.myapplication.Model;

import java.util.List;

public class FitnessPlan {
    //
    private String title;
    private String level;
    private int id;
    private String duration;
    private String image; // URL to the image
    private String description;

    private List<Day> days;

    public FitnessPlan(){}


    public FitnessPlan(List<Day> days, String title, String level, String duration, String image, String description) {
        this.days = days;
        this.id = id;
        this.title = title;
        this.level = level;
        this.duration = duration;
        this.image = image;
        this.description = description;
    }

    // Getters and Setters
    public List<Day> getDays(){
        return this.days;
    }

    public int getId(){
        return id;
    }
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

    public void setId(int id){
        this.id = id;
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