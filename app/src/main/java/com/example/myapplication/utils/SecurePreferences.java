// SecurePreferences.java
package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import android.util.Log;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Set;

public class SecurePreferences {
    private static final String TAG = "SecurePreferences";
    private final SharedPreferences sharedPreferences;

    public SecurePreferences(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    "secure_prefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            Log.e(TAG, "Error initializing EncryptedSharedPreferences", e);
            throw new RuntimeException(e);
        }
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * Retrieves all keys stored in SecurePreferences.
     *
     * @return Set of all keys.
     */
    public Set<String> getAllKeys() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        return allEntries.keySet();
    }
}
