package com.example.relife.Helpers;

import com.example.relife.Model.User;

public class UserHelper {

    User user;

    public UserHelper(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isValidData() {
        return getUser().getName().equals("") && getUser().getPhoneNumber().length() < 10;
    }
}
