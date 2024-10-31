// RegistrationViewModel.java
package com.example.myapplication.ui.registration;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import com.example.myapplication.utils.UserManager;
import java.io.IOException;
import java.security.GeneralSecurityException;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class RegistrationViewModel extends AndroidViewModel {

    private static final String TAG = "RegistrationViewModel";
    private static final String PREFS_FILENAME = "user_prefs";
    private SharedPreferences encryptedSharedPreferences;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        try {
            MasterKey masterKey = new MasterKey.Builder(application)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    application,
                    PREFS_FILENAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            Log.e(TAG, "Error initializing EncryptedSharedPreferences", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Registers a new user.
     *
     * @param username Username.
     * @param password Password.
     * @return true if registration successful, false if user already exists.
     */
    public boolean register(String username, String password) {
        if (encryptedSharedPreferences.contains(username)) {
            // User already exists
            return false;
        }

        // Hash password using bcrypt
        String hashedPassword = hashPassword(password);

        // Save user credentials
        encryptedSharedPreferences.edit().putString(username, hashedPassword).apply();
        return true;
    }

    private String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
