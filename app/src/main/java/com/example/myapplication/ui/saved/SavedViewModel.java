package com.example.myapplication.ui.saved;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.repository.PasswordRepository;

import java.util.List;

/**
 * ViewModel for SavedFragment.
 */
public class SavedViewModel extends AndroidViewModel {

    private final PasswordRepository passwordRepository;
    private final LiveData<List<Account>> allAccountsLiveData;

    public SavedViewModel(@NonNull Application application) {
        super(application);
        passwordRepository = PasswordRepository.getInstance(application);
        allAccountsLiveData = passwordRepository.getAllAccountsLiveData();
    }

    /**
     * Returns LiveData of all accounts.
     *
     * @return LiveData list of accounts.
     */
    public LiveData<List<Account>> getAllAccountsLiveData() {
        return allAccountsLiveData;
    }

    /**
     * Updates an existing account.
     *
     * @param updatedAccount Updated account.
     * @param position       Position of the account in the list.
     */
    public void updateAccount(Account updatedAccount, int position) {
        passwordRepository.updateAccount(updatedAccount, position);
    }

    /**
     * Deletes an account at the specified position.
     *
     * @param position Position of the account to delete.
     */
    public void deleteAccount(int position) {
        passwordRepository.deleteAccount(position);
    }
}
