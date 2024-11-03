package com.example.myapplication.ui.saved;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

import java.util.List;

/**
 * Adapter for displaying accounts in RecyclerView.
 */
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private List<Account> accounts;
    private final OnItemClickListener listener;

    /**
     * Interface for handling button clicks.
     */
    public interface OnItemClickListener {
        void onCopyClick(Account account);
        void onEditClick(Account account, int position);
    }

    /**
     * Constructor for AccountAdapter.
     *
     * @param accounts List of accounts to display.
     * @param listener Listener for button clicks.
     */
    public AccountAdapter(List<Account> accounts, OnItemClickListener listener) {
        this.accounts = accounts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accounts.get(position);
        holder.serviceTextView.setText("Service: " + account.getService());
        holder.emailTextView.setText("Email: " + account.getEmail());

        // Set masked password
        holder.passwordTextView.setText("Password: ********");

        // Set click listeners for buttons
        holder.copyButton.setOnClickListener(v -> listener.onCopyClick(account));
        holder.editButton.setOnClickListener(v -> listener.onEditClick(account, position));
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    /**
     * Updates the list of accounts and notifies the adapter of data changes.
     *
     * @param newAccounts New list of accounts to display.
     */
    public void updateAccounts(List<Account> newAccounts) {
        this.accounts = newAccounts;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder for account items.
     */
    static class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView serviceTextView;
        TextView emailTextView;
        TextView passwordTextView;
        ImageButton copyButton;
        ImageButton editButton;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceTextView = itemView.findViewById(R.id.serviceTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            passwordTextView = itemView.findViewById(R.id.passwordTextView);
            copyButton = itemView.findViewById(R.id.copyButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
}
