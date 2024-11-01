// GenerationFragment.java
package com.example.myapplication.ui.generation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.R;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Fragment for generating and saving passwords.
 */
public class GenerationFragment extends Fragment {

    private TextView generatedPasswordTextView;
    private SeekBar passwordLengthSeekBar;
    private TextView passwordLengthText;
    private CheckBox specialSymbolsCheckbox;
    private GenerationViewModel generationViewModel;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generation, container, false);

        // Initialize UI elements
        generatedPasswordTextView = view.findViewById(R.id.textView);
        passwordLengthSeekBar = view.findViewById(R.id.passwordLengthSeekBar);
        passwordLengthText = view.findViewById(R.id.passwordLengthText);
        specialSymbolsCheckbox = view.findViewById(R.id.specialSymbolsCheckbox);
        Button generatePasswordButton = view.findViewById(R.id.generatePasswordButton);
        Button savePasswordButton = view.findViewById(R.id.savePasswordButton);

        // Initialize ViewModel
        generationViewModel = new ViewModelProvider(this).get(GenerationViewModel.class);

        // Set SeekBar properties
        passwordLengthSeekBar.setMax(20);
        passwordLengthSeekBar.setProgress(8);
        passwordLengthText.setText("Password Length: 8");

        // Update password length text when SeekBar changes
        passwordLengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int length = Math.max(8, progress); // Minimum length 8
                passwordLengthText.setText("Password Length: " + length);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        // Set generate button click listener
        generatePasswordButton.setOnClickListener(v -> generatePassword());

        // Set save button click listener
        savePasswordButton.setOnClickListener(v -> savePassword(view));

        return view;
    }

    /**
     * Generates a random password and displays it.
     */
    private void generatePassword() {
        int length = Math.max(8, passwordLengthSeekBar.getProgress());
        boolean includeSpecialSymbols = specialSymbolsCheckbox.isChecked();
        String generatedPassword = generateRandomPassword(length, includeSpecialSymbols);
        generatedPasswordTextView.setText(generatedPassword);
    }

    /**
     * Generates a random password based on specified parameters.
     *
     * @param length               Password length.
     * @param includeSpecialSymbols Whether to include special symbols.
     * @return Generated password.
     */
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

        // Ensure at least one character from each category
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        if (includeSpecialSymbols) {
            password.append(special.charAt(random.nextInt(special.length())));
        }

        for (int i = password.length(); i < length; i++) {
            int index = random.nextInt(charSet.length());
            password.append(charSet.charAt(index));
        }

        // Shuffle characters
        List<Character> passwordChars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            passwordChars.add(c);
        }
        Collections.shuffle(passwordChars, random);

        StringBuilder finalPassword = new StringBuilder();
        for (char c : passwordChars) {
            finalPassword.append(c);
        }

        return finalPassword.toString();
    }

    /**
     * Saves the generated password for the current user.
     */
    private void savePassword(View view) {
        String service = ((EditText) view.findViewById(R.id.serviceEditText)).getText().toString().trim();
        String email = ((EditText) view.findViewById(R.id.emailEditText)).getText().toString().trim();
        String password = generatedPasswordTextView.getText().toString();

        if (TextUtils.isEmpty(service) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Service and Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save password using ViewModel
        generationViewModel.savePassword(service, email, password);
        Toast.makeText(getContext(), "Password saved successfully", Toast.LENGTH_SHORT).show();
    }
}
