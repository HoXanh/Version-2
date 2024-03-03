package com.fitfusion.myapplication.Model;

import java.util.List;

public class Day {
    private String day;
    private List<Exercise> exercises;

    public Day(){}

    public Day(String day, List<Exercise> exercises){
        this.day = day;
        this.exercises = exercises;
    }

    public List<Exercise> getExercises(){
        return this.exercises;
    }

    public String getDay(){
        return this.day;
    }
}
