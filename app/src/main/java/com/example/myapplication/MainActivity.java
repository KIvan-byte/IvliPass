package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.myapplication.utils.UserManager;
import android.view.View;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize NavHostFragment and NavController
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        bottomNavigationView = findViewById(R.id.nav_view);
        // Setup BottomNavigationView with NavController
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Listener to manage BottomNavigationView visibility
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.loginFragment ||
                    destination.getId() == R.id.registrationFragment) {
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void navigateToHome() {
        navController.navigate(R.id.action_loginFragment_to_navigation_home);
    }

    public void logout() {
        // Clear current user
        UserManager.getInstance().clearCurrentUser();

        // Navigate to login screen with popUpTo to prevent back navigation
        navController.navigate(R.id.action_navigation_home_to_loginFragment,
                null,
                new NavOptions.Builder()
                        .setPopUpTo(R.id.navigation_home, true)
                        .build());
    }
}
