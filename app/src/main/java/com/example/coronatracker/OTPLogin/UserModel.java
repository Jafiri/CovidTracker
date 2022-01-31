package com.example.coronatracker.OTPLogin;

public class UserModel {
    String phonenumber;

    public UserModel(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public UserModel() {
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
