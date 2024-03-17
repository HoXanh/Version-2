package com.fitfusion.myapplication;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, qualifiers = "w820dp-h720dp")
public class LoginTest {
    //This unit test: email and password
    @Test
    public void validateInput_EmptyEmail_ReturnsFalse() {
        Login login = new Login();
        boolean result = login.validateInput("", "password");
        System.out.println("validateInput_EmptyEmail_ReturnsFalse result: " + result);
        assertFalse(result);
    }

    @Test
    public void validateInput_EmptyPassword_ReturnsFalse() {
        Login login = new Login();
        boolean result = login.validateInput("email@example.com", "");
        System.out.println("validateInput_EmptyPassword_ReturnsFalse result: "+ result);
        assertFalse(result);
    }

    @Test
    public void validateInput_ValidInput_ReturnsTrue() {
        Login login = new Login();
        boolean result = login.validateInput("email@example.com", "password");
        System.out.println("validateInput_ValidInput_ReturnsTrue result: " + result);
        assertTrue(result);
    }
    @Test
    public void password_Valid_IfLengthIsSix() {
        assertTrue(Login.isPasswordValid("123456"));
    }

    @Test
    public void password_Valid_IfMoreThanSixCharacters() {
        assertTrue(Login.isPasswordValid("1234567"));
    }

    @Test
    public void password_Invalid_IfLessThanSixCharacters() {
        assertFalse(Login.isPasswordValid("12345"));
    }

    @Test
    public void password_Invalid_IfEmpty() {
        assertFalse(Login.isPasswordValid(""));
    }

    @Test
    public void password_Invalid_IfNull() {
        assertFalse(Login.isPasswordValid(null));
    }


}
