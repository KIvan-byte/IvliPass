// UserManager.java
package com.example.myapplication.utils;

public class UserManager {
    private static UserManager instance;
    private String currentUsername;

    private UserManager() {
        currentUsername = null;
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void clearCurrentUser() {
        currentUsername = null;
    }
}
