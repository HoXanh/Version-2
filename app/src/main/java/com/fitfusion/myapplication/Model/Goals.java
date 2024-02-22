package com.fitfusion.myapplication.Model;

import java.util.ArrayList;

public class Goals {
    private String fitnessGoal;  // muscle gain, weight loss
    private String bodyType;     // Skinny, Fat
    private Boolean physicalWork; // Is your current job physical? Answer: yes or no;
    private String trainingDays;  // 1 day per week, 3 days per week, 5 days per week
    private String exerciseMotivation; // to gain strength, improving cardiovascular health, injury


    public Goals(String fitnessGoal, String bodyType, Boolean physicalWork, String trainingDays, String exerciseMotivation) {
        this.fitnessGoal = fitnessGoal;
        this.bodyType = bodyType;
        this.physicalWork = physicalWork;
        this.trainingDays = trainingDays;
        this.exerciseMotivation = exerciseMotivation;
    }

    public Boolean getPhysicalWork() {
        return physicalWork;
    }

    public void setPhysicalWork(Boolean physicalWork) {
        this.physicalWork = physicalWork;
    }

    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getExerciseMotivation() {
        return exerciseMotivation;
    }

    public void setExerciseMotivation(String exerciseMotivation) {
        this.exerciseMotivation = exerciseMotivation;
    }

    public String getTrainingDays() {
        return trainingDays;
    }

    public void setTrainingDays(String trainingDays) {
        this.trainingDays = trainingDays;
    }
}


