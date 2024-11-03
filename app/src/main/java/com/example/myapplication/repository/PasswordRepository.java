package com.example.myapplication.repository;

import android.app.Application;
import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

/**
 * Repository for managing password data.
 */
public class PasswordRepository {
    private static final String TAG = "PasswordRepository";
    private static PasswordRepository instance;
    private final SecurePreferences securePreferences;
    private final KeyStoreManager keyStoreManager;
    private final MutableLiveData<List<Account>> accountsLiveData = new MutableLiveData<>();

    private PasswordRepository(Application application) {
        securePreferences = new SecurePreferences(application);
        keyStoreManager = new KeyStoreManager();
        keyStoreManager.generateKey();
        loadAllAccounts();
    }

    /**
     * Initializes the singleton instance of PasswordRepository.
     *
     * @param application Application context.
     * @return Singleton instance.
     */
    public static synchronized PasswordRepository getInstance(Application application) {
        if (instance == null) {
            instance = new PasswordRepository(application);
        }
        return instance;
    }

    /**
     * Returns LiveData of all accounts.
     *
     * @return LiveData list of accounts.
     */
    public LiveData<List<Account>> getAllAccountsLiveData() {
        return accountsLiveData;
    }

    /**
     * Saves a new account.
     *
     * @param service  Service name.
     * @param email    User email (optional).
     * @param password Generated password.
     */
    public void savePassword(String service, String email, String password) {
        String username = UserManager.getInstance().getCurrentUsername();
        if (username == null) {
            Log.e(TAG, "No authenticated user");
            return;
        }

        try {
            Cipher cipher = keyStoreManager.getCipherForEncryption();
            if (cipher == null) {
                Log.e(TAG, "Failed to initialize cipher for encryption");
                return;
            }

            byte[] encryptionIv = cipher.getIV();
            byte[] encryptedPassword = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));

            // Encode in Base64 for storage
            String encryptedPasswordBase64 = Base64.encodeToString(encryptedPassword, Base64.DEFAULT);
            String encryptionIvBase64 = Base64.encodeToString(encryptionIv, Base64.DEFAULT);

            // Create a new JSON object for the account
            JSONObject accountObject = new JSONObject();
            accountObject.put("service", service);
            accountObject.put("email", email);
            accountObject.put("password", encryptedPasswordBase64);
            accountObject.put("iv", encryptionIvBase64);

            // Get existing user accounts
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

            Log.d(TAG, "Password for service '" + service + "' and email '" + email + "' successfully saved for user '" + username + "'");

            // Reload all accounts to update LiveData
            loadAllAccounts();

        } catch (GeneralSecurityException | JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error encrypting password or handling JSON", e);
        }
    }

    /**
     * Updates an existing account at the specified position.
     *
     * @param updatedAccount Updated account.
     * @param position       Position of the account in the list.
     */
    public void updateAccount(Account updatedAccount, int position) {
        String username = UserManager.getInstance().getCurrentUsername();
        if (username == null) {
            Log.e(TAG, "No authenticated user");
            return;
        }

        String accountsKey = "accounts_" + username;
        String accountsJson = securePreferences.getString(accountsKey, null);

        if (accountsJson == null) {
            Log.e(TAG, "Accounts for user '" + username + "' not found");
            return;
        }

        try {
            JSONArray accountsArray = new JSONArray(accountsJson);
            if (position < 0 || position >= accountsArray.length()) {
                Log.e(TAG, "Invalid account position: " + position);
                return;
            }

            String service = updatedAccount.getService();
            String email = updatedAccount.getEmail();
            String password = updatedAccount.getPassword();

            // Encrypt the updated password
            Cipher cipher = keyStoreManager.getCipherForEncryption();
            if (cipher == null) {
                Log.e(TAG, "Failed to initialize cipher for encryption");
                return;
            }

            byte[] encryptionIv = cipher.getIV();
            byte[] encryptedPassword = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));

            String encryptedPasswordBase64 = Base64.encodeToString(encryptedPassword, Base64.DEFAULT);
            String encryptionIvBase64 = Base64.encodeToString(encryptionIv, Base64.DEFAULT);

            // Update the JSON object for the account
            JSONObject updatedAccountObject = new JSONObject();
            updatedAccountObject.put("service", service);
            updatedAccountObject.put("email", email);
            updatedAccountObject.put("password", encryptedPasswordBase64);
            updatedAccountObject.put("iv", encryptionIvBase64);

            // Replace the old account with the updated one
            accountsArray.put(position, updatedAccountObject);

            // Save the updated accounts array
            securePreferences.putString(accountsKey, accountsArray.toString());

            Log.d(TAG, "Account at position " + position + " successfully updated for user '" + username + "'");

            // Reload all accounts to update LiveData
            loadAllAccounts();

        } catch (GeneralSecurityException | JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error updating account", e);
        }
    }

    /**
     * Deletes an account at the specified position.
     *
     * @param position Position of the account to delete.
     */
    public void deleteAccount(int position) {
        String username = UserManager.getInstance().getCurrentUsername();
        if (username == null) {
            Log.e(TAG, "No authenticated user");
            return;
        }

        String accountsKey = "accounts_" + username;
        String accountsJson = securePreferences.getString(accountsKey, null);

        if (accountsJson == null) {
            Log.e(TAG, "Accounts for user '" + username + "' not found");
            return;
        }

        try {
            JSONArray accountsArray = new JSONArray(accountsJson);
            if (position < 0 || position >= accountsArray.length()) {
                Log.e(TAG, "Invalid account position: " + position);
                return;
            }

            // Remove the account at the specified position
            accountsArray.remove(position);

            // Save the updated accounts array
            securePreferences.putString(accountsKey, accountsArray.toString());

            Log.d(TAG, "Account at position " + position + " successfully deleted for user '" + username + "'");

            // Reload all accounts to update LiveData
            loadAllAccounts();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error deleting account", e);
        }
    }

    /**
     * Loads all accounts for the current user and updates LiveData.
     */
    private void loadAllAccounts() {
        String username = UserManager.getInstance().getCurrentUsername();
        if (username == null) {
            Log.e(TAG, "No authenticated user");
            accountsLiveData.postValue(new ArrayList<>());
            return;
        }

        String accountsKey = "accounts_" + username;
        String accountsJson = securePreferences.getString(accountsKey, null);

        List<Account> accounts = new ArrayList<>();

        if (accountsJson == null) {
            accountsLiveData.postValue(accounts);
            return;
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
                    Log.e(TAG, "Failed to initialize cipher for decryption");
                    continue;
                }

                try {
                    byte[] decryptedPassword = cipher.doFinal(encryptedPassword);
                    String password = new String(decryptedPassword, StandardCharsets.UTF_8);
                    accounts.add(new Account(service, email, password));
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Error decrypting password for service: " + service, e);
                }
            }

            accountsLiveData.postValue(accounts);
            Log.d(TAG, "Total accounts loaded: " + accounts.size());

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error parsing JSON for accounts", e);
            accountsLiveData.postValue(new ArrayList<>());
        }
    }
}
