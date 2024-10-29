// CheckViewModel.java
package com.example.myapplication.ui.check;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class CheckViewModel extends AndroidViewModel {

    private Set<String> commonPasswords;
    public MutableLiveData<String> passwordStrength = new MutableLiveData<>();

    public CheckViewModel(Application application) {
        super(application);
        loadCommonPasswords();
    }

    private void loadCommonPasswords() {
        commonPasswords = new HashSet<>();
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
    }


    public void checkPasswordStrength(String password) {
        if (commonPasswords.contains(password)) {
            passwordStrength.setValue("Very Weak (Common Password)");
            return;
        }

        int strengthScore = calculateStrengthScore(password);
        if (strengthScore <= 2) passwordStrength.setValue("Weak");
        else if (strengthScore == 3) passwordStrength.setValue("Moderate");
        else passwordStrength.setValue("Strong");
    }

    private int calculateStrengthScore(String password) {
        int score = 0;
        if (password.length() >= 8) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*\\d.*")) score++;
        if (password.matches(".*[!@#$%^&*+=?-].*")) score++;
        return score;
    }
}
