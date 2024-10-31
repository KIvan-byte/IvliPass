// KeyStoreManager.java
package com.example.myapplication.utils;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class KeyStoreManager {
    private static final String TAG = "KeyStoreManager";
    private static final String KEY_ALIAS = "MyAppKeyAlias";
    private static final String ANDROID_KEYSTORE = "AndroidKeyStore";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private KeyStore keyStore;

    public KeyStoreManager() {
        try {
            keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
            keyStore.load(null);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            Log.e(TAG, "Error initializing KeyStore", e);
        }
    }

    public void generateKey() {
        try {
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(
                        KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT
                )
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setKeySize(256)
                        .build();

                KeyGenerator keyGenerator = KeyGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE);
                keyGenerator.init(keyGenParameterSpec);
                keyGenerator.generateKey();
            }
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException |
                 KeyStoreException | NoSuchProviderException e) {
            Log.e(TAG, "Error generating key", e);
        }
    }

    /**
     * Initializes Cipher for encryption.
     *
     * @return Initialized Cipher or null if error.
     */
    public Cipher getCipherForEncryption() {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            return cipher;
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Cipher for encryption", e);
            return null;
        }
    }

    /**
     * Initializes Cipher for decryption.
     *
     * @param iv Initialization vector.
     * @return Initialized Cipher or null if error.
     */
    public Cipher getCipherForDecryption(byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), new GCMParameterSpec(128, iv));
            return cipher;
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Cipher for decryption", e);
            return null;
        }
    }

    private SecretKey getSecretKey() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        return (SecretKey) keyStore.getKey(KEY_ALIAS, null);
    }
}
