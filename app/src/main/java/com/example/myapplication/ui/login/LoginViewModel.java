package com.example.myapplication.ui.login;// LoginViewModel.java
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    // Простой метод для проверки логина
    public boolean isLoginValid(String username, String password) {
        // В этом примере логин действителен, если имя пользователя и пароль не пустые
        // Вы можете заменить это условие на свою логику проверки
        return username.equals("admin") && password.equals("password");
    }
}
