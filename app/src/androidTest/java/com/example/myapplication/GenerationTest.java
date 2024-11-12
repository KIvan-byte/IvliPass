package com.example.myapplication;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.action.ViewActions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)  // Выберите порядок сортировки
@RunWith(AndroidJUnit4.class)
public class GenerationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    // Статические переменные, доступные для всех тестов
    public static String username = "Ivan";
    public static String password = "password123";
    public static String service = "WSPA";
    public static String email = "user@gmail.com";

    @Test
    public void test1_user_can_enter_service_name(){
        Espresso.onView(ViewMatchers.withId(R.id.registerTextView)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.registerButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.serviceEditText)).perform(ViewActions.typeText(service));
    }
    @Test
    public void test2_user_can_enter_email(){
        Espresso.onView(ViewMatchers.withId(R.id.registerTextView)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.registerButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.emailEditText)).perform(ViewActions.typeText(email));
    }
    @Test
    public void test3_user_can_activate_special_symbols(){
        Espresso.onView(ViewMatchers.withId(R.id.registerTextView)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.registerButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.specialSymbolsCheckbox)).perform(ViewActions.click());
    }
    @Test
    public void test4_user_can_interact_with_seek_bar(){
        Espresso.onView(ViewMatchers.withId(R.id.registerTextView)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.registerButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.passwordLengthSeekBar)).perform(ViewActions.swipeRight());
    }
    @Test
    public void test5_user_can_generate_password(){
        Espresso.onView(ViewMatchers.withId(R.id.registerTextView)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.registerButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.serviceEditText)).perform(ViewActions.typeText(service));
        Espresso.onView(ViewMatchers.withId(R.id.generatePasswordButton)).perform(ViewActions.click());
    }
    @Test
    public void test5_user_can_save_data(){
        Espresso.onView(ViewMatchers.withId(R.id.registerTextView)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.registerButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.serviceEditText)).perform(ViewActions.typeText(service));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.generatePasswordButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.savePasswordButton)).perform(ViewActions.click());
    }
}

