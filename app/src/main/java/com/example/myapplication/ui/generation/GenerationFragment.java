package com.example.myapplication.ui.generation;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.myapplication.R;
import java.security.SecureRandom;

public class GenerationFragment extends Fragment {

    private TextView generatedPasswordTextView;
    private SeekBar passwordLengthSeekBar;
    private TextView passwordLengthText;
    private CheckBox specialSymbolsCheckbox;
    private GenerationViewModel generationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generation, container, false);

        generatedPasswordTextView = view.findViewById(R.id.textView);
        passwordLengthSeekBar = view.findViewById(R.id.passwordLengthSeekBar);
        passwordLengthText = view.findViewById(R.id.passwordLengthText);
        specialSymbolsCheckbox = view.findViewById(R.id.specialSymbolsCheckbox);
        Button generatePasswordButton = view.findViewById(R.id.generatePasswordButton);
        Button savePasswordButton = view.findViewById(R.id.savePasswordButton);

        // Update the password length text when the SeekBar changes
        passwordLengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passwordLengthText.setText("Password Length: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        // Generate password
        generatePasswordButton.setOnClickListener(v -> generatePassword());

        // Save password logic to be implemented
        savePasswordButton.setOnClickListener(v -> savePassword());

        return view;
    }

    private void generatePassword() {
        int length = passwordLengthSeekBar.getProgress();
        boolean includeSpecialSymbols = specialSymbolsCheckbox.isChecked();
        String generatedPassword = generateRandomPassword(length, includeSpecialSymbols);
        generatedPasswordTextView.setText(generatedPassword);
    }

    private String generateRandomPassword(int length, boolean includeSpecialSymbols) {
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        String special = "!@#$%^&*()_+-=[]{}|;':\",.<>?";

        StringBuilder charSet = new StringBuilder(lower + upper + digits);
        if (includeSpecialSymbols) {
            charSet.append(special);
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charSet.length());
            password.append(charSet.charAt(index));
        }
        return password.toString();
    }

    private void savePassword() {
        // Implement your logic to save the password securely
        String service = ((TextView) getView().findViewById(R.id.serviceEditText)).getText().toString();
        String email = ((TextView) getView().findViewById(R.id.emailEditText)).getText().toString();
        String password = generatedPasswordTextView.getText().toString();

        if (TextUtils.isEmpty(service) || TextUtils.isEmpty(password)) {
            // Show an error or a Toast message
            return;
        }

        // Call your ViewModel method to save the password to the encrypted database
        generationViewModel.savePassword(service, email, password);
    }
}
