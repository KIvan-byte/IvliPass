// Account.java
package com.example.myapplication.ui.saved;

import java.io.Serializable;

/**
 * Model class representing an account.
 */
public class Account implements Serializable {
    private final String service;
    private final String email;
    private final String password;

    public Account(String service, String email, String password) {
        this.service = service;
        this.email = email;
        this.password = password;
    }

    public String getService() {
        return service;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
