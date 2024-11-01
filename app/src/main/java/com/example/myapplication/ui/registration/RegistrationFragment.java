// RegistrationFragment.java
package com.example.myapplication.ui.registration;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class RegistrationFragment extends Fragment {

    private RegistrationViewModel registrationViewModel;
    private EditText usernameEditText, passwordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        // Инициализация ViewModel
        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        // Инициализация элементов интерфейса
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        Button registerButton = view.findViewById(R.id.registerButton);

        // Установка обработчика для кнопки регистрации
        registerButton.setOnClickListener(this::registerUser);

        return view;
    }

    private void registerUser(View view) {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Проверка ввода
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Username and Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Вызов метода регистрации в ViewModel
        boolean success = registrationViewModel.register(username, password);

        if (success) {
            Toast.makeText(getContext(), "Registration successful", Toast.LENGTH_SHORT).show();
            // Переход на экран входа
            Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_loginFragment);
        } else {
            Toast.makeText(getContext(), "User already exists", Toast.LENGTH_SHORT).show();
        }
    }
}
