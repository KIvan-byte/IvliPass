package com.example.myapplication.ui.saved;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * DialogFragment for editing account details.
 */
public class CopyEditDialogFragment extends DialogFragment {

    private static final String ARG_ACCOUNT = "arg_account";
    private static final String ARG_IS_EDIT = "arg_is_edit";
    private static final String ARG_POSITION = "arg_position";

    private Account account;
    private boolean isEdit;
    private int position;

    private TextInputLayout serviceInputLayout;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextInputEditText serviceEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private Button cancelButton;
    private Button confirmButton;
    private ImageButton deleteButton;

    /**
     * Creates a new instance of CopyEditDialogFragment.
     *
     * @param account  Account to display/edit.
     * @param isEdit   Flag indicating if the dialog is in edit mode.
     * @param position Position of the account in the list (used for updates).
     * @return New instance of CopyEditDialogFragment.
     */
    public static CopyEditDialogFragment newInstance(Account account, boolean isEdit, int position) {
        CopyEditDialogFragment fragment = new CopyEditDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACCOUNT, account);
        args.putBoolean(ARG_IS_EDIT, isEdit);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Retrieve arguments
        if (getArguments() != null) {
            account = (Account) getArguments().getSerializable(ARG_ACCOUNT);
            isEdit = getArguments().getBoolean(ARG_IS_EDIT, false);
            position = getArguments().getInt(ARG_POSITION, -1);
        }

        // Inflate the dialog layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_copy_edit_account, null);

        // Initialize UI elements
        serviceInputLayout = view.findViewById(R.id.dialogServiceInputLayout);
        emailInputLayout = view.findViewById(R.id.dialogEmailInputLayout);
        passwordInputLayout = view.findViewById(R.id.dialogPasswordInputLayout);
        serviceEditText = view.findViewById(R.id.dialogServiceEditText);
        emailEditText = view.findViewById(R.id.dialogEmailEditText);
        passwordEditText = view.findViewById(R.id.dialogPasswordEditText);
        cancelButton = view.findViewById(R.id.dialogCancelButton);
        confirmButton = view.findViewById(R.id.dialogConfirmButton);
        deleteButton = view.findViewById(R.id.dialogDeleteButton);

        // Populate fields with account data
        if (account != null) {
            serviceEditText.setText(account.getService());
            emailEditText.setText(account.getEmail());
            passwordEditText.setText(account.getPassword());
            // Set password visibility to visible in edit mode
            passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }

        // If not in edit mode, disable editing fields and hide delete button
        if (!isEdit) {
            serviceEditText.setEnabled(false);
            passwordEditText.setEnabled(false);
            confirmButton.setText(R.string.copy);
            deleteButton.setVisibility(View.GONE);
        } else {
            confirmButton.setText(R.string.save);
            deleteButton.setVisibility(View.VISIBLE);
        }

        // Set click listeners for buttons
        cancelButton.setOnClickListener(v -> dismiss());

        confirmButton.setOnClickListener(v -> {
            if (isEdit) {
                // Handle saving the edited account
                String newEmail = emailEditText.getText().toString().trim();
                String newPassword = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(newEmail)) {
                    emailInputLayout.setError("Email cannot be empty");
                    return;
                } else {
                    emailInputLayout.setError(null);
                }

                if (TextUtils.isEmpty(newPassword)) {
                    passwordInputLayout.setError("Password cannot be empty");
                    return;
                } else {
                    passwordInputLayout.setError(null);
                }

                // Create the updated account
                Account updatedAccount = new Account(account.getService(), newEmail, newPassword);

                // Send the updated account back to the fragment
                if (getTargetFragment() instanceof SavedFragment) {
                    ((SavedFragment) getTargetFragment()).onAccountEdited(updatedAccount, position);
                }

                dismiss();
            } else {
                // In copy mode, do nothing as copying is handled directly
                dismiss();
            }
        });

        // Handle delete button click
        deleteButton.setOnClickListener(v -> showDeleteConfirmation());

        // Create and return the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(view);
        builder.setCancelable(true);

        return builder.create();
    }

    /**
     * Shows a confirmation dialog for deleting the account.
     */
    private void showDeleteConfirmation() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete this account?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (getTargetFragment() instanceof SavedFragment) {
                        ((SavedFragment) getTargetFragment()).onDeleteClick(account, position);
                    }
                    dismiss();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
