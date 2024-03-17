package com.fitfusion.myapplication;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

@RunWith(RobolectricTestRunner.class)
public class ProfileTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private FirebaseAuth mockAuth;
    @Mock
    private FirebaseUser mockFirebaseUser;
    @Mock
    private DatabaseReference mockDatabaseReference;
    // Assuming UserProfile is your model with appropriate constructors and getters.
    @Mock
    private DataSnapshot dataSnapshot;

    private Profile profile;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        profile = new Profile();
        // Directly inject mocked Firebase Auth and Database reference
        profile.setFirebaseAuth(mockAuth);
        profile.setDatabaseReference(mockDatabaseReference);

        when(mockAuth.getCurrentUser()).thenReturn(mockFirebaseUser);
        when(mockFirebaseUser.getUid()).thenReturn("testUserId");
    }

    @Test
    public void showUserProfile_UserExists_UserDetailsShown() {
        UserProfile mockUserProfile = new UserProfile("John Doe", "john@example.com", "Male", "01/01/1990", "180", "80", "imageURL");

        when(mockDatabaseReference.child("testUserId")).thenReturn(mockDatabaseReference);
        when(dataSnapshot.getValue(UserProfile.class)).thenReturn(mockUserProfile);

        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0, ValueEventListener.class);
            listener.onDataChange(dataSnapshot);
            return null;
        }).when(mockDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        profile.showUserProfile2(mockFirebaseUser);

        // Now verify that your UI elements are set as expected.
        // Since actual UI changes require an Android environment, these verifications might be
        // more suitable for an instrumented test (Espresso). However, you can still check the logic.

        // Example (pseudo-code, won't run as is):
         assertEquals("John Doe", profile.usernameTV.getText().toString());
    }
}
