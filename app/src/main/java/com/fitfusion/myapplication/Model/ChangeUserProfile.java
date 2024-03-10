package com.fitfusion.myapplication.Model;

public class ChangeUserProfile {


    private String username, dob, gender,height,weight, imageUrl;

    public ChangeUserProfile(){

    }

    public ChangeUserProfile(String username, String dob, String gender, String height, String weight) {
        this.username = username;
        this.dob = dob;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }




}
