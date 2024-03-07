package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitfusion.myapplication.Model.UserProfile;
import com.fitfusion.myapplication.Model.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private TextView usernameTV,emailTV, dobTV,genderTV, heightTV, weightTV;
    private ImageView profileImageView;
    private String username, email, dob, gender, height, weight;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profilepic);
        usernameTV = findViewById(R.id.usernameTV);
        emailTV = findViewById(R.id.emailTV);
        dobTV = findViewById(R.id.dobTV);
        genderTV = findViewById(R.id.gender_Text_View);
        heightTV = findViewById(R.id.heightTV);
        weightTV = findViewById(R.id.weightTV);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser fbUser = authProfile.getCurrentUser();

        if (fbUser == null) {
            Toast.makeText(Profile.this, "Something went wrong | User's details are not available at the moment",
                    Toast.LENGTH_SHORT).show();
        } else {
            showUserProfile(fbUser);
        }
//
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.fitnessPg:
                    // Intent to go to Home Activity
                    startActivity(new Intent(Profile.this, Planner.class));
                    return true;
                case R.id.foodPg:
                    startActivity(new Intent(Profile.this, Blog.class));

                    return true;
                case R.id.blogPg:
                    // Intent to go to Notifications Activity
                    startActivity(new Intent(Profile.this, Blog.class));
                    return true;

                case R.id.profilePg:
                    startActivity(new Intent(Profile.this, Profile.class));
                    return true;

                case R.id.homePg:
                    startActivity(new Intent(Profile.this, MainActivity.class));
                    return true;
            }
            return false;
        });
    }

    private void showUserProfile(FirebaseUser fbUser) {
        String userID = fbUser.getUid();

        DatabaseReference refProfile = FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app").getReference("Registered Users");
        refProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile readUserDetails = snapshot.getValue(UserProfile.class);
                if (readUserDetails != null){
                    username = readUserDetails.getUsername();
                    email = fbUser.getEmail();
                    dob = readUserDetails.getDob();
                    gender = readUserDetails.getGender();
                    height = readUserDetails.getHeight();
                    weight = readUserDetails.getWeight();

                    usernameTV.setText(username);
                    emailTV.setText(email);
                    dobTV.setText(dob);
                    genderTV.setText(gender);
                    heightTV.setText(height + " cm");
                    weightTV.setText(weight + " kg");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something went wrong!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}