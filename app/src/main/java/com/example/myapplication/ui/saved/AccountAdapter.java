// AccountAdapter.java
package com.example.myapplication.ui.saved;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private List<Account> accounts;

    public AccountAdapter(List<Account> accounts) {
        this.accounts = accounts;
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
        holder.passwordTextView.setText("Password: " + account.getPassword());
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    /**
     * Updates the list of accounts and refreshes the RecyclerView.
     *
     * @param newAccounts New list of accounts to display.
     */
    public void updateAccounts(List<Account> newAccounts) {
        this.accounts = newAccounts;
        notifyDataSetChanged();
    }

    static class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView serviceTextView;
        TextView emailTextView;
        TextView passwordTextView;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceTextView = itemView.findViewById(R.id.serviceTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            passwordTextView = itemView.findViewById(R.id.passwordTextView);
        }
    }
}
