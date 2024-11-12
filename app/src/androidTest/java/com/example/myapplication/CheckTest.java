package com.example.myapplication;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)  // Выберите порядок сортировки
@RunWith(AndroidJUnit4.class)
public class CheckTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    // Статические переменные, доступные для всех тестов
    public static String username = "Ivan";
    public static String password = "password123";
    public static String service = "WSPA";
    public static String email = "user@gmail.com";

    @Test
    public void test1_user_can_go_to_the_check_window() {
        Espresso.onView(ViewMatchers.withId(R.id.registerTextView)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.registerButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.navigation_dashboard)).perform(ViewActions.click());
    }
    @Test
    public void test2_user_can_check_his_password(){
        Espresso.onView(ViewMatchers.withId(R.id.registerTextView)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.registerButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.navigation_dashboard)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.navigation_notifications)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
    }
}