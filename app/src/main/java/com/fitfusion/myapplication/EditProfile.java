package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fitfusion.myapplication.Model.ChangeUserProfile;
import com.fitfusion.myapplication.Model.UserProfile;
import com.fitfusion.myapplication.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class EditProfile extends AppCompatActivity {
    private EditText usernameET, dobET,genderET, heightET, weightET;
    private String username, dob, gender, height,weight, imageUrl, email;

    private TextView emailTV;
    private ImageButton editProfileBtn;
    private ImageView profilepic;
    private String[] userData;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userData = getIntent().getStringArrayExtra("UserPassData");
        emailTV = findViewById(R.id.emailTV);
        profilepic = findViewById(R.id.profilepic);
        usernameET = findViewById(R.id.usernameET);
        dobET = findViewById(R.id.dobET);
        genderET = findViewById(R.id.genderET);
        heightET = findViewById(R.id.heightET);
        weightET = findViewById(R.id.weightET);

        editProfileBtn = findViewById(R.id.changeProfileBtn);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser fbUser = authProfile.getCurrentUser();
        showUser(userData);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editProfle(fbUser);
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.fitnessPg:
                    // Intent to go to Home Activity
                    startActivity(new Intent(EditProfile.this, Planner.class));
                    return true;
                case R.id.foodPg:
                    startActivity(new Intent(EditProfile.this, Blog.class));

                    return true;
                case R.id.blogPg:
                    // Intent to go to Notifications Activity
                    startActivity(new Intent(EditProfile.this, Blog.class));
                    return true;

                case R.id.profilePg:
                    startActivity(new Intent(EditProfile.this, Profile.class));
                    return true;

                case R.id.homePg:
                    startActivity(new Intent(EditProfile.this, MainActivity.class));
                    return true;
            }
            return false;
        });




    }

    private void editProfle(FirebaseUser fbUser) {

        String userID = fbUser.getUid();

        username = usernameET.getText().toString().trim();
        dob = dobET.getText().toString().trim();
        gender = genderET.getText().toString().trim();
        height = heightET.getText().toString().trim();
        weight = weightET.getText().toString().trim();


        Users updateUserProfile = new Users(username, email, gender, dob, height, weight);
        updateUserProfile.setImageUrl(imageUrl);
        DatabaseReference refEditProfile = FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app").getReference("Registered Users");
        refEditProfile.child(userID).setValue(updateUserProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditProfile.this,"Update User Profile Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfile.this, Profile.class));
                finish();
            }
        });


    }


    private void showUser(String[] userData) {
        username = userData[0];
        dob = userData[1];
        gender = userData[2];
        height = userData[3];
        weight = userData[4];
        imageUrl = userData[5];
        email = userData[6];

        if (imageUrl != null) {
            Glide.with(EditProfile.this).load(imageUrl).circleCrop().into(profilepic);
        }
        emailTV.setText(email);
        usernameET.setText(username);
        dobET.setText(dob);
        genderET.setText(gender);
        heightET.setText(height);
        weightET.setText(weight);
    }


}