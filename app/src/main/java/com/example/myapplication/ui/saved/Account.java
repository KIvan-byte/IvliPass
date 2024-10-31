// Account.java
package com.example.myapplication.ui.saved;

public class Account {
    private String service;
    private String email;
    private String password;

    public Account(String service, String email, String password) {
        this.service = service;
        this.email = email;
        this.password = password;
    }

    public String getService() { return service; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
