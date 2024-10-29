package com.example.myapplication.ui.login;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private EditText usernameEditText, passwordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Инициализируем ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Инициализация элементов интерфейса
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        Button loginButton = view.findViewById(R.id.loginButton);

        // Устанавливаем обработчик для кнопки входа
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (loginViewModel.isLoginValid(username, password)) {
                    // Переход на HomeFragment
                    ((MainActivity) requireActivity()).navigateToHome();
                } else {
                    Toast.makeText(getContext(), "Invalid login or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
