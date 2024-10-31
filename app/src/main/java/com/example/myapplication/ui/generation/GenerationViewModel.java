// GenerationViewModel.java
package com.example.myapplication.ui.generation;

import android.app.Application;
import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.example.myapplication.ui.saved.Account;
import com.example.myapplication.utils.KeyStoreManager;
import com.example.myapplication.utils.SecurePreferences;
import com.example.myapplication.utils.UserManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

public class GenerationViewModel extends AndroidViewModel {

    private static final String TAG = "GenerationViewModel";
    private SecurePreferences securePreferences;
    private KeyStoreManager keyStoreManager;

    public GenerationViewModel(Application application) {
        super(application);
        securePreferences = new SecurePreferences(application);
        keyStoreManager = new KeyStoreManager();
        keyStoreManager.generateKey();
    }

    /**
     * Saves the encrypted password along with IV for the current user using JSON.
     *
     * @param service  Service name.
     * @param email    User's email (optional).
     * @param password Generated password.
     */
    public void savePassword(String service, String email, String password) {
        String username = UserManager.getInstance().getCurrentUsername();
        if (username == null) {
            Log.e(TAG, "No user is currently logged in");
            return;
        }

        try {
            Cipher cipher = keyStoreManager.getCipherForEncryption();
            if (cipher == null) {
                Log.e(TAG, "Cipher initialization failed");
                return;
            }

            byte[] encryptionIv = cipher.getIV();
            byte[] encryptedPassword = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));

            // Encode in Base64 for storage
            String encryptedPasswordBase64 = Base64.encodeToString(encryptedPassword, Base64.DEFAULT);
            String encryptionIvBase64 = Base64.encodeToString(encryptionIv, Base64.DEFAULT);

            // Create a new account JSON object
            JSONObject accountObject = new JSONObject();
            accountObject.put("service", service);
            accountObject.put("email", email);
            accountObject.put("password", encryptedPasswordBase64);
            accountObject.put("iv", encryptionIvBase64);

            // Retrieve existing accounts for the user
            String accountsKey = "accounts_" + username;
            String existingAccountsJson = securePreferences.getString(accountsKey, null);
            JSONArray accountsArray;

            if (existingAccountsJson != null) {
                accountsArray = new JSONArray(existingAccountsJson);
            } else {
                accountsArray = new JSONArray();
            }

            // Add the new account to the array
            accountsArray.put(accountObject);

            // Save the updated accounts array
            securePreferences.putString(accountsKey, accountsArray.toString());

            Log.d(TAG, "Password for service '" + service + "' and email '" + email + "' saved successfully for user '" + username + "'");
        } catch (GeneralSecurityException | JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error encrypting password or handling JSON", e);
        }
    }

    /**
     * Retrieves all accounts for the current user.
     *
     * @return List of Account objects.
     */
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String username = UserManager.getInstance().getCurrentUsername();

        if (username == null) {
            Log.e(TAG, "No user is currently logged in");
            return accounts;
        }

        String accountsKey = "accounts_" + username;
        String accountsJson = securePreferences.getString(accountsKey, null);

        if (accountsJson == null) {
            return accounts;
        }

        try {
            JSONArray accountsArray = new JSONArray(accountsJson);
            for (int i = 0; i < accountsArray.length(); i++) {
                JSONObject accountObject = accountsArray.getJSONObject(i);
                String service = accountObject.getString("service");
                String email = accountObject.getString("email");
                String encryptedPasswordBase64 = accountObject.getString("password");
                String ivBase64 = accountObject.getString("iv");

                byte[] encryptedPassword = Base64.decode(encryptedPasswordBase64, Base64.DEFAULT);
                byte[] encryptionIv = Base64.decode(ivBase64, Base64.DEFAULT);

                Cipher cipher = keyStoreManager.getCipherForDecryption(encryptionIv);
                if (cipher == null) {
                    Log.e(TAG, "Cipher initialization failed for decryption");
                    continue;
                }

                try {
                    byte[] decryptedPassword = cipher.doFinal(encryptedPassword);
                    String password = new String(decryptedPassword, StandardCharsets.UTF_8);
                    accounts.add(new Account(service, email, password));
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Error decrypting password for email: " + email, e);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error parsing JSON for accounts", e);
        }

        return accounts;
    }
}
