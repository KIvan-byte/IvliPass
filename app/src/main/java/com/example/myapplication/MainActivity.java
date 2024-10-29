package com.example.myapplication;

import android.os.Bundle;

import com.example.myapplication.ui.login.LoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

//public class MainActivity extends AppCompatActivity {
//
//    private ActivityMainBinding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
//    }
//
//}








import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация навигационного контроллера
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Инициализация нижней навигации
        navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);

        // Скрываем нижнюю навигацию по умолчанию
        navView.setVisibility(View.GONE);

        // Проверяем, есть ли сохраненное состояние (например, при повороте экрана)
        if (savedInstanceState == null) {
            // Если нет, переходим на LoginFragment
            navController.navigate(R.id.loginFragment);
        }
    }

    // Метод для перехода на домашнюю страницу и отображения нижней навигации
    public void navigateToHome() {
        navController.navigate(R.id.navigation_home);
        // Показываем нижнюю навигацию
        navView.setVisibility(View.VISIBLE);
    }
}

