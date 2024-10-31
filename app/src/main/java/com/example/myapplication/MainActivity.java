// MainActivity.java
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.myapplication.utils.UserManager;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = navHostFragment.getNavController();

        bottomNavigationView = findViewById(R.id.nav_view);
        // Настройка BottomNavigationView с NavController
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Слушатель изменений навигации для управления видимостью BottomNavigationView
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.loginFragment || destination.getId() == R.id.registrationFragment) {
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
        // Очистка текущего пользователя
        UserManager.getInstance().clearCurrentUser();

        // Навигация на экран входа
        navController.navigate(R.id.action_navigation_home_to_loginFragment);
    }
}
