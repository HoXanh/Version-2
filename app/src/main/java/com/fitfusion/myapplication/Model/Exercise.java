package com.fitfusion.myapplication.Model;

public class Exercise {
    private String name, reps;

    public Exercise(){}

    public Exercise(String name, String reps){
        this.name = name;
        this.reps = reps;
    }

    public String getName(){
        return this.name;
    }

    public String getReps(){
        return this.reps;
    }
}
