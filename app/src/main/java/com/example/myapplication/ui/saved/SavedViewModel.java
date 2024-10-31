// SavedViewModel.java
package com.example.myapplication.ui.saved;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myapplication.repository.PasswordRepository;

import java.util.List;

public class SavedViewModel extends AndroidViewModel {

    private PasswordRepository passwordRepository;

    public SavedViewModel(@NonNull Application application) {
        super(application);
        passwordRepository = PasswordRepository.getInstance(application.getApplicationContext());
    }

    /**
     * Retrieves all accounts for the current user.
     *
     * @return List of Account objects.
     */
    public List<Account> getAllAccounts() {
        return passwordRepository.getAllAccounts();
    }
}
