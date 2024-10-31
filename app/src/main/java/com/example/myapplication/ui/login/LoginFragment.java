// LoginFragment.java
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
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.utils.UserManager;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Initialize UI elements
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        registerTextView = view.findViewById(R.id.registerTextView);

        // Set login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loginUser(); }
        });

        // Set register TextView click listener
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registrationFragment);
            }
        });

        return view;
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (loginViewModel.isLoginValid(username, password)) {
            // Устанавливаем текущего пользователя
            UserManager.getInstance().setCurrentUsername(username);

            // Переход на HomeFragment
            ((MainActivity) getActivity()).navigateToHome();
        } else {
            Toast.makeText(getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
