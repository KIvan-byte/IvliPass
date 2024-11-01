// PasswordRepository.java
package com.example.myapplication.repository;

import android.app.Application;
import android.content.Context;


import com.example.myapplication.ui.saved.Account;
import com.example.myapplication.ui.generation.GenerationViewModel;

import java.util.List;

public class PasswordRepository {
    private static PasswordRepository instance;
    private final GenerationViewModel generationViewModel;

    private PasswordRepository(Context context) {
        // Initialize ViewModel
        // **Note**: It's generally not recommended to instantiate ViewModels directly. Consider using a factory or dependency injection.
        generationViewModel = new GenerationViewModel((Application) context.getApplicationContext());
    }

    public static synchronized PasswordRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PasswordRepository(context);
        }
        return instance;
    }

    public void savePassword(String service, String email, String password) {
        generationViewModel.savePassword(service, email, password);
    }

    public List<Account> getAllAccounts() {
        return generationViewModel.getAllAccounts();
    }
}
