package com.fitfusion.myapplication;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<Login> activityRule = new ActivityScenarioRule<>(Login.class);

    @Test
    public void checkComponentsVisibility() {
        // Check if email, password fields, and login button are displayed
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.log_in_button)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToSignUp() {
        // Click on the sign up button
        onView(withId(R.id.signUpBtn)).perform(ViewActions.click());
        // Use an actual view ID from RegistryActivity to verify the navigation
        onView(withId(R.id.register)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginButtonWithEmptyFields() {
        // Click the login button without entering any text
        onView(withId(R.id.log_in_button)).perform(ViewActions.click());

        // Verify the Toast message appears
        onView(withText("Email or Password is Empty!!!"))
                .inRoot(new ToastMatcher()) // Use your custom ToastMatcher
                .check(matches(isDisplayed()));
    }



    // Additional tests can include typing into the email and password fields,
    // but handling successful or failed login will require mocking Firebase Auth or using a testing account.
}
