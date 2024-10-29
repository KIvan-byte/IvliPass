package com.example.myapplication.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentCheckBinding;

public class CheckFragment extends Fragment {

    private FragmentCheckBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CheckViewModel notificationsViewModel =
                new ViewModelProvider(this).get(CheckViewModel.class);

        binding = FragmentCheckBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

////        final TextView textView = binding.editTextText;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}