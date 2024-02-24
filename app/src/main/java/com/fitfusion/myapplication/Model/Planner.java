package com.fitfusion.myapplication.Model;

public class Planner {
    //
    String exercisePicture;
    String exerciseName;
    String reps;
    String sets;


    public Planner(String exercisePicture, String exerciseName, String reps, String sets) {
        this.exercisePicture = exercisePicture;
        this.exerciseName = exerciseName;
        this.reps = reps;
        this.sets = sets;
    }


    public String getExercisePicture() {
        return exercisePicture;
    }

    public void setExercisePicture(String exercisePicture) {
        this.exercisePicture = exercisePicture;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }



}