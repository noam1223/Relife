package com.example.relife.Model;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String phoneNumber;

    public User() {
    }

    public User(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }


    public String getName() {
        return name;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
