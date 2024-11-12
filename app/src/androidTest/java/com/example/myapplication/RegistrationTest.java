package com.example.myapplication;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
public class RegistrationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    // Переменные для username и password
    private final String username = "Ivan";
    private final String password = "password123";

    @Test
    public void test1_useAppContext() {
        // Context of the app under test
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myapplication", appContext.getPackageName());
    }

    @Test
    public void test2_user_can_enter_username_while_registration() {
        // Проверка, что можно ввести текст в поле ввода username
        Espresso.onView(ViewMatchers.withId(R.id.registerTextView)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
    }

    @Test
    public void test3_user_can_enter_password_while_registration(){
        // Проверка, что можно ввести текст в поле ввода password
        Espresso.onView(ViewMatchers.withId(R.id.registerTextView)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
    }

    @Test
    public void test4_user_can_click_register_button(){
        Espresso.onView(ViewMatchers.withId(R.id.registerTextView)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.registerButton)).perform(ViewActions.click());
    }

    @Test
    public void test5_user_can_register_in_application(){
        Espresso.onView(ViewMatchers.withId(R.id.registerTextView)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.usernameEditText)).perform(ViewActions.typeText(username));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.registerButton)).perform(ViewActions.click());
        //Espresso.onView(ViewMatchers.withText("Registration Successful")).check(matches(isDisplayed()));
    }
}

