package com.fitfusion.myapplication.Model;


public class Users {


    //unit_settings: convert for US users
    private String username,email,gender,dob,height,weight,imageUrl;



    public Users(String username, String email, String gender, String dob, String height, String weight){
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
//        this.imageUrl = imageUrl;

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender=gender;}


    public String getDob() {return dob;}
    public void setDob(String Dob) {this.dob = dob;}


    public String getHeight() {return height;}
    public void setHeight(String height) {this.height = height;}


    public String getWeight() {return weight;}
    public void setWeight(String weight) {this.weight = weight;}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

