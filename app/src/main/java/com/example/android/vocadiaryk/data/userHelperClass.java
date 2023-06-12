package com.example.android.vocadiaryk.data;

public class userHelperClass {
    String name, mob, dob, password;

    public userHelperClass(){

    }

    public userHelperClass(String name, String mob, String dob, String password){
        this.name = name;
        this.mob = mob;
        this.dob = dob;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
