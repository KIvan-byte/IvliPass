// GenerationViewModel.java
package com.example.myapplication.ui.generation;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myapplication.repository.PasswordRepository;

/**
 * ViewModel for the GenerationFragment.
 */
public class GenerationViewModel extends AndroidViewModel {

    private static final String TAG = "GenerationViewModel";
    private final PasswordRepository passwordRepository;

    public GenerationViewModel(@NonNull Application application) {
        super(application);
        passwordRepository = PasswordRepository.getInstance(application);
    }

    /**
     * Saves the generated password.
     *
     * @param service  Service name.
     * @param email    User's email (optional).
     * @param password Generated password.
     */
    public void savePassword(String service, String email, String password) {
        passwordRepository.savePassword(service, email, password);
        Log.d(TAG, "Password saved for service: " + service);
    }
}
