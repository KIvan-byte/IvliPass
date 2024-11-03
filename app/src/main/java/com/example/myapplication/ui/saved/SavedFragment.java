package com.example.myapplication.ui.saved;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for displaying saved accounts.
 */
public class SavedFragment extends Fragment implements AccountAdapter.OnItemClickListener {

    private SavedViewModel savedViewModel;
    private AccountAdapter accountAdapter;
    private List<Account> allAccounts = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_saved, container, false);

        // Check if user is authenticated
        if (UserManager.getInstance().getCurrentUsername() == null) {
            // Navigate to login screen
            Navigation.findNavController(root).navigate(R.id.action_savedFragment_to_loginFragment);
            return root;
        }

        // Initialize UI elements
        EditText searchServiceEditText = root.findViewById(R.id.searchServiceEditText);
        RecyclerView accountsRecyclerView = root.findViewById(R.id.accountsRecyclerView);
        accountsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        savedViewModel = new ViewModelProvider(this).get(SavedViewModel.class);

        // Initialize adapter with empty list and set listener
        accountAdapter = new AccountAdapter(new ArrayList<>(), this);
        accountsRecyclerView.setAdapter(accountAdapter);

        // Observe changes in account list
        savedViewModel.getAllAccountsLiveData().observe(getViewLifecycleOwner(), accounts -> {
            allAccounts = accounts;
            accountAdapter.updateAccounts(allAccounts);
        });

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
                    // If search string is empty, display all accounts
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
     * Filters the list of accounts based on the search query.
     *
     * @param query Search query entered by the user.
     * @return List of accounts matching the query.
     */
    private List<Account> filterAccounts(String query) {
        List<Account> filteredList = new ArrayList<>();
        for (Account account : allAccounts) {
            // Check if service or email contains the query string
            if (account.getService().toLowerCase().contains(query) ||
                    account.getEmail().toLowerCase().contains(query)) {
                filteredList.add(account);
            }
        }
        return filteredList;
    }

    /**
     * Handles the "Copy" button click.
     *
     * @param account Account to copy.
     */
    @Override
    public void onCopyClick(Account account) {
        // Copy password to clipboard
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Password", account.getPassword());
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Password copied to clipboard.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Failed to copy password.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles the "Edit" button click.
     *
     * @param account  Account to edit.
     * @param position Position of the account in the list.
     */
    @Override
    public void onEditClick(Account account, int position) {
        // Open dialog to edit account details
        CopyEditDialogFragment dialog = CopyEditDialogFragment.newInstance(account, true, position);
        dialog.setTargetFragment(this, 0);
        dialog.show(getParentFragmentManager(), "EditDialog");
    }

    /**
     * Handles the account being edited, received from the dialog.
     *
     * @param updatedAccount Updated account.
     * @param position       Position of the account in the list.
     */
    public void onAccountEdited(Account updatedAccount, int position) {
        // Update account in repository
        savedViewModel.updateAccount(updatedAccount, position);

        // Show confirmation
        Toast.makeText(getContext(), "Account successfully updated.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles the account being deleted, received from the dialog.
     *
     * @param account  Account to delete.
     * @param position Position of the account in the list.
     */
    public void onDeleteClick(Account account, int position) {
        // Delete account from repository
        savedViewModel.deleteAccount(position);

        // Show confirmation
        Toast.makeText(getContext(), "Account successfully deleted.", Toast.LENGTH_SHORT).show();
    }
}
