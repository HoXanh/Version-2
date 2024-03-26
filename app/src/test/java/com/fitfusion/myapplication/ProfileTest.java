package com.fitfusion.myapplication;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.os.Build;

import com.fitfusion.myapplication.Model.UserProfile;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class ProfileTest {

    private Profile profileActivity;

    @Mock
    private FirebaseAuth mockFirebaseAuth;
    @Mock
    private FirebaseUser mockFirebaseUser;
    @Mock
    private DatabaseReference mockDatabaseReference;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        profileActivity = new Profile();
        // Assuming you can set FirebaseAuth and DatabaseReference via methods or reflection
        profileActivity.setFirebaseAuth(mockFirebaseAuth);
        profileActivity.setDatabaseReference(mockDatabaseReference);
        when(mockFirebaseAuth.getCurrentUser()).thenReturn(mockFirebaseUser);
        when(mockFirebaseUser.getUid()).thenReturn("testUserId");
    }

    @Test
    public void testShowUserProfile() {
        UserProfile testUserProfile = new UserProfile("testUsername", "testEmail", "testDob", "testGender", "testHeight", "testWeight", "testImageUrl");
        when(mockDatabaseReference.child("testUserId")).thenReturn(mockDatabaseReference);
        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            DataSnapshot mockSnapshot = mock(DataSnapshot.class);
            when(mockSnapshot.getValue(UserProfile.class)).thenReturn(testUserProfile);
            listener.onDataChange(mockSnapshot);
            return null;
        }).when(mockDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        profileActivity.showUserProfile(mockFirebaseUser);

        // Assert that the user profile data is displayed correctly
        assertEquals("testUsername", profileActivity.usernameTV.getText().toString());
        // Add assertions for other fields like emailTV, dobTV, etc.
    }


    // Add test methods here
}
