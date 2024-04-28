package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fitfusion.myapplication.Model.UserProfile;
import com.fitfusion.myapplication.Model.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    public TextView usernameTV;
    private TextView emailTV;
    private TextView dobTV;
    private TextView genderTV;
    private TextView heightTV;
    private TextView weightTV;

    private DatabaseReference refProfile;
    private ImageView profileImageView;
//    private FloatingActionButton homeBtn;
    private ImageButton moveToEditProfileBtn, logOutBtn;
    private String username, email, dob, gender, height, weight, imageUrl;
    private FirebaseAuth authProfile;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    public void setFirebaseAuth(FirebaseAuth authProfile) {
        this.authProfile = authProfile;
    }

    public void setDatabaseReference(DatabaseReference refProfile) {
        this.refProfile = refProfile;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        homeBtn = findViewById(R.id.fab);
//        homeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Profile.this, MainActivity.class));
//            }
//        });
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.fitnessPg:
                        // Intent to go to Home Activity
                        startActivity(new Intent(Profile.this, Planner.class));
                        return true;
                    case R.id.foodPg:
                        // Intent to go to Dashboard Activity
                        startActivity(new Intent(Profile.this, Planner.class));
                        return true;
                    case R.id.blogPg:
                        Intent intent = new Intent(Profile.this, Blog.class);
                        startActivity(intent);
                        return true;

                    case R.id.profilePg:
                        startActivity(new Intent(Profile.this, Profile.class));
                        return true;

                    case R.id.homePg:
                        startActivity(new Intent(Profile.this, MainActivity.class));
                        return true;

                    case R.id.signout:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(Profile.this, "Logged Out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Profile.this, Login.class));
//                        finish();
                        return true;
                }
                return false;
            }
        });

        profileImageView = findViewById(R.id.profilepic);
        usernameTV = findViewById(R.id.usernameTV);
        emailTV = findViewById(R.id.emailTV);
        dobTV = findViewById(R.id.dobTV);
        genderTV = findViewById(R.id.gender_Text_View);
        heightTV = findViewById(R.id.heightTV);
        weightTV = findViewById(R.id.weightTV);
        logOutBtn = findViewById(R.id.logout);

        moveToEditProfileBtn = findViewById(R.id.my_circular_image_button);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser fbUser = authProfile.getCurrentUser();

        if (fbUser == null) {
            Toast.makeText(Profile.this, "Something went wrong | User's details are not available at the moment",
                    Toast.LENGTH_SHORT).show();
        } else {
            showUserProfile(fbUser);
        }
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Profile.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Profile.this, Login.class));
                finish();
            }
        });
        moveToEditProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] passUserProfile = {username, dob, gender,height,weight, imageUrl, email};
                passUserData(passUserProfile);
            }
        });
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.fitnessPg:
//                    // Intent to go to Home Activity
//                    startActivity(new Intent(Profile.this, Planner.class));
//                    return true;
//                case R.id.foodPg:
//                    startActivity(new Intent(Profile.this, Blog.class));
//
//                    return true;
//                case R.id.blogPg:
//                    // Intent to go to Notifications Activity
//                    startActivity(new Intent(Profile.this, Blog.class));
//                    return true;
//
//                case R.id.profilePg:
//                    startActivity(new Intent(Profile.this, Profile.class));
//                    return true;
//
//                case R.id.homePg:
//                    startActivity(new Intent(Profile.this, MainActivity.class));
//                    return true;
//            }
//            return false;
//        });
    }
    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer((GravityCompat.START));
        }
        else {
            super.onBackPressed();
        }

    }



    private void passUserData(String[] userData) {
        Intent intent = new Intent(Profile.this, EditProfile.class);
        intent.putExtra("UserPassData", userData);
        startActivity(intent);
    }

    public void showUserProfile(FirebaseUser fbUser) {
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
                    imageUrl = readUserDetails.getImageUrl();

                    if (imageUrl != null) {
                        Glide.with(Profile.this).load(imageUrl).circleCrop().into(profileImageView);
                    }
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

    public void showUserProfile2(FirebaseUser fbUser) {
        String userID = fbUser.getUid();

        // Use the injected DatabaseReference
        refProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile readUserDetails = snapshot.getValue(UserProfile.class);
                if (readUserDetails != null) {
                    // Update UI components with user details...
                    username = readUserDetails.getUsername();
                    email = fbUser.getEmail();
                    dob = readUserDetails.getDob();
                    gender = readUserDetails.getGender();
                    height = readUserDetails.getHeight();
                    weight = readUserDetails.getWeight();
                    imageUrl = readUserDetails.getImageUrl();

//                    if (imageUrl != null) {
//                        Glide.with(Profile.this).load(imageUrl).circleCrop().into(profileImageView);
//                    }
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
                // Handle potential errors...
            }
        });
    }
}