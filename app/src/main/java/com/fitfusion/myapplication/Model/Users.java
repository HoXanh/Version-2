package com.fitfusion.myapplication.Model;

public class Users {
    //unit_settings: convert for US users
    private String name,password,email,image,date,gender,age,height,weight,units_settings;

    public Users(String name, String password, String email, String image, String gender, String date, String height, String weight, String units_settings){
        this.name = name;
        this.password = password;
        this.email = email;
        this.image = image;
        this.date = date;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.units_settings = units_settings;
    }

    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {return password;}
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {return image;}
    public void setDate(String date) {this.date = date;}

    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender=gender;}

    public String getAge() {return age;}
    public void setAge(String age) {this.age = age;}

    public String getHeight() {return height;}
    public void setHeight(String height) {this.height = height;}

    public String getWeight() {return weight;}
    public void setWeight(String weight) {this.weight = weight;}

    public String getUnits_settings() {return units_settings;}
    public void setUnits_settings(String units_settings) {this.units_settings = units_settings;}
}
