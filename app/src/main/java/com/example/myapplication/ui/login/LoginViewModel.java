// LoginViewModel.java
package com.example.myapplication.ui.login;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";
    private static final String PREFS_FILENAME = "user_prefs";
    private final SharedPreferences encryptedSharedPreferences;

    public LoginViewModel(@NonNull Application application) {
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
     * Validates login credentials.
     *
     * @param username Username.
     * @param password Password.
     * @return true if credentials are valid, false otherwise.
     */
    public boolean isLoginValid(String username, String password) {
        if (!encryptedSharedPreferences.contains(username)) {
            return false;
        }

        String storedHashedPassword = encryptedSharedPreferences.getString(username, null);
        if (storedHashedPassword == null) {
            return false;
        }

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), storedHashedPassword);
        return result.verified;
    }
}
