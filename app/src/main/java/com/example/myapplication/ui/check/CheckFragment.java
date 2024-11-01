// CheckFragment.java
package com.example.myapplication.ui.check;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.example.myapplication.R;

public class CheckFragment extends Fragment {

    private CheckViewModel checkViewModel;
    private TextView strengthIndicator;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, container, false);

        checkViewModel = new ViewModelProvider(this).get(CheckViewModel.class);
        EditText passwordInput = view.findViewById(R.id.passwordEditText);
        strengthIndicator = view.findViewById(R.id.strengthIndicator);

        checkViewModel.passwordStrength.observe(getViewLifecycleOwner(), strength -> {
            strengthIndicator.setText("Password Strength: " + strength);
        });

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkViewModel.checkPasswordStrength(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        return view;
    }
}
