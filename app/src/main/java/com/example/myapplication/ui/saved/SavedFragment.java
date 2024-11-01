// SavedFragment.java
package com.example.myapplication.ui.saved;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {

    private SavedViewModel savedViewModel;
    private AccountAdapter accountAdapter;
    private List<Account> allAccounts = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_saved, container, false);

        // Check if user is logged in
        if (UserManager.getInstance().getCurrentUsername() == null) {
            // Redirect to login screen
            Navigation.findNavController(root).navigate(R.id.action_savedFragment_to_loginFragment);
            return root;
        }

        // Initialize UI elements
        EditText searchServiceEditText = root.findViewById(R.id.searchServiceEditText);
        RecyclerView accountsRecyclerView = root.findViewById(R.id.accountsRecyclerView);
        accountsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        savedViewModel = new ViewModelProvider(this).get(SavedViewModel.class);

        // Initialize Adapter with empty list
        accountAdapter = new AccountAdapter(new ArrayList<>());
        accountsRecyclerView.setAdapter(accountAdapter);

        // Fetch all accounts and display
        loadAllAccounts();

        // Set up TextWatcher for dynamic search
        searchServiceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim().toLowerCase();
                if (!TextUtils.isEmpty(query)) {
                    List<Account> filteredList = filterAccounts(query);
                    if (filteredList.isEmpty()) {
                        Toast.makeText(getContext(), "No matching accounts found.", Toast.LENGTH_SHORT).show();
                    }
                    accountAdapter.updateAccounts(filteredList);
                } else {
                    // If search query is empty, display all accounts
                    accountAdapter.updateAccounts(allAccounts);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
        });

        return root;
    }

    /**
     * Loads all accounts from ViewModel and updates the RecyclerView.
     */
    private void loadAllAccounts() {
        allAccounts = savedViewModel.getAllAccounts();
        if (allAccounts.isEmpty()) {
            Toast.makeText(getContext(), "No saved accounts found.", Toast.LENGTH_SHORT).show();
        }
        accountAdapter.updateAccounts(allAccounts);
    }

    /**
     * Filters the list of accounts based on the search query.
     *
     * @param query The search query entered by the user.
     * @return A list of accounts that match the query.
     */
    private List<Account> filterAccounts(String query) {
        List<Account> filteredList = new ArrayList<>();
        for (Account account : allAccounts) {
            // Check if service or email contains the query string
            if (account.service().toLowerCase().contains(query) ||
                    account.email().toLowerCase().contains(query)) {
                filteredList.add(account);
            }
        }
        return filteredList;
    }
}
