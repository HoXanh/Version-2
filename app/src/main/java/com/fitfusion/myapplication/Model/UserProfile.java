package com.fitfusion.myapplication.Model;




public class UserProfile {
    //unit_settings: convert for US users
    public String username, gender, dob,height,weight;


    public UserProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(UserProfile.class)
    }
    public UserProfile(String username, String gender, String dob, String height, String weight){
        this.username = username;
        this.gender = gender;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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