// CheckViewModel.java
package com.example.myapplication.ui.check;

import android.app.Application;
import android.graphics.Color;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CheckViewModel extends AndroidViewModel {

    private Set<String> commonPasswords;
    private MutableLiveData<PasswordStrength> _passwordStrength = new MutableLiveData<>();
    public LiveData<PasswordStrength> passwordStrength = _passwordStrength;
    private ExecutorService executorService;

    public CheckViewModel(Application application) {
        super(application);
        executorService = Executors.newSingleThreadExecutor();
        loadCommonPasswords();
    }

    private void loadCommonPasswords() {
        commonPasswords = new HashSet<>();
        executorService.execute(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        getApplication().getAssets().open("rockyou.txt")));
                String line;
                while ((line = reader.readLine()) != null) {
                    commonPasswords.add(line.trim());
                }
                reader.close();
                Log.d("CheckViewModel", "rockyou.txt loaded successfully.");
            } catch (Exception e) {
                Log.e("CheckViewModel", "Failed to load rockyou.txt", e);
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }

    public void checkPasswordStrength(String password) {
        // Ensure that commonPasswords is loaded
        if (commonPasswords.isEmpty()) {
            _passwordStrength.setValue(new PasswordStrength("Loading...", Color.GRAY, 0));
            return;
        }

        if (commonPasswords.contains(password)) {
            _passwordStrength.postValue(new PasswordStrength("Very Weak (Common Password)", Color.RED, 25));
            return;
        }

        // Calculate entropy
        double entropy = calculateEntropy(password);

        // Determine strength based on entropy and other factors
        PasswordStrength strength = evaluateStrength(password, entropy);
        _passwordStrength.postValue(strength);
    }

    private double calculateEntropy(String password) {
        int pool = 0;
        if (password.matches(".*[a-z].*")) pool += 26;
        if (password.matches(".*[A-Z].*")) pool += 26;
        if (password.matches(".*\\d.*")) pool += 10;
        if (password.matches(".*[!@#$%^&*()+-].*")) pool += 10; // Adjust based on symbols used

        if (pool == 0) pool = 1; // Prevent log(0)

        return password.length() * (Math.log(pool) / Math.log(2)); // Entropy in bits
    }

    private PasswordStrength evaluateStrength(String password, double entropy) {
        // Basic thresholds for entropy
        // Adjust these thresholds based on desired sensitivity
        if (entropy < 28) {
            return new PasswordStrength("Very Weak", Color.RED, 25);
        } else if (entropy < 35) {
            return new PasswordStrength("Weak", Color.parseColor("#FF9800"), 50); // Orange
        } else if (entropy < 59) {
            return new PasswordStrength("Moderate", Color.YELLOW, 75);
        } else {
            return new PasswordStrength("Strong", Color.GREEN, 100);
        }
    }

    // Inner class to hold password strength details
    public static class PasswordStrength {
        private final String label;
        private final int color;
        private final int progress;

        public PasswordStrength(String label, int color, int progress) {
            this.label = label;
            this.color = color;
            this.progress = progress;
        }

        public String getLabel() {
            return label;
        }

        public int getColor() {
            return color;
        }

        public int getProgress() {
            return progress;
        }
    }
}
