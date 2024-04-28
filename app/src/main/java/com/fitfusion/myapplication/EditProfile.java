//package com.fitfusion.myapplication;
//
//
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.fitfusion.myapplication.Model.ChangeUserProfile;
//import com.fitfusion.myapplication.Model.UserProfile;
//import com.fitfusion.myapplication.Model.Users;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import org.w3c.dom.Text;
//
//public class EditProfile extends AppCompatActivity {
//    private static final int PICK_IMAGE_REQUEST = 1;
//    private EditText usernameET, dobET,genderET, heightET, weightET;
//    private String username, dob, gender, height,weight, imageUrl, email;
//
//    private TextView emailTV;
//    private ImageButton editProfileBtn, changeProfileImageBtn;
//    private ImageView profilepic;
//    private String[] userData;
//    private FirebaseAuth authProfile;
//    private Uri imageUri;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_profile);
//        userData = getIntent().getStringArrayExtra("UserPassData");
//        emailTV = findViewById(R.id.emailTV);
//        profilepic = findViewById(R.id.profilepic);
//        usernameET = findViewById(R.id.usernameET);
//        dobET = findViewById(R.id.dobET);
//        genderET = findViewById(R.id.genderET);
//        heightET = findViewById(R.id.heightET);
//        weightET = findViewById(R.id.weightET);
//
//        editProfileBtn = findViewById(R.id.changeProfileBtn);
//        changeProfileImageBtn = findViewById(R.id.changeProfileImageBtn);
//
//        authProfile = FirebaseAuth.getInstance();
//        FirebaseUser fbUser = authProfile.getCurrentUser();
//        showUser(userData);
//
//        changeProfileImageBtn.setOnClickListener(v -> openFileChooser());
//
//        // Save changes button
//        editProfileBtn.setOnClickListener(v -> {
//            if (fbUser != null) {
//                editProfile(fbUser);
//            }
//        });
//
//
//
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.fitnessPg:
//                    // Intent to go to Home Activity
//                    startActivity(new Intent(EditProfile.this, Planner.class));
//                    return true;
//                case R.id.foodPg:
//                    startActivity(new Intent(EditProfile.this, Blog.class));
//
//                    return true;
//                case R.id.blogPg:
//                    // Intent to go to Notifications Activity
//                    startActivity(new Intent(EditProfile.this, Blog.class));
//                    return true;
//
//                case R.id.profilePg:
//                    startActivity(new Intent(EditProfile.this, Profile.class));
//                    return true;
//
//                case R.id.homePg:
//                    startActivity(new Intent(EditProfile.this, MainActivity.class));
//                    return true;
//            }
//            return false;
//        });
//
//
//
//
//    }
//
//
//    private void openFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            imageUri = data.getData();
//            Glide.with(this).load(imageUri).into(profilepic);
//        }
//    }
//
//    private void uploadImage(FirebaseUser fbUser, Users user) {
//        if (imageUri != null) {
//            StorageReference fileReference = FirebaseStorage.getInstance().getReference("uploads").child(fbUser.getUid() + ".jpg");
//
//            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
//                        String imageUrl = uri.toString();
//                        user.setImageUrl(imageUrl); // Assuming Users model has an imageUrl field setter
//                        updateUserDetails(fbUser, user);
//                    });
//                }
//            }).addOnFailureListener(e -> Toast.makeText(EditProfile.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//        } else {
//            // If no image was selected, proceed to save other details
//            updateUserDetails(fbUser, user);
//        }
//    }
//
//    private void updateUserDetails(FirebaseUser fbUser, Users user) {
//        DatabaseReference refProfile = FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app").getReference("Registered Users");
//        refProfile.child(fbUser.getUid()).setValue(user).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Toast.makeText(EditProfile.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(EditProfile.this, Login.class));
//                finish();
//            } else {
//                Toast.makeText(EditProfile.this, "Failed to register: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void editProfile(FirebaseUser fbUser) {
//
//        String userID = fbUser.getUid();
//
//        username = usernameET.getText().toString().trim();
//        dob = dobET.getText().toString().trim();
//        gender = genderET.getText().toString().trim();
//        height = heightET.getText().toString().trim();
//        weight = weightET.getText().toString().trim();
//
//
//
//        UserProfile updateUserProfile = new UserProfile(username, email, gender, dob, height, weight, imageUrl);
//        updateUserProfile.setImageUrl(imageUrl);
//        DatabaseReference refEditProfile = FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app").getReference("Registered Users");
//        refEditProfile.child(userID).setValue(updateUserProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(EditProfile.this,"Update User Profile Successful", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(EditProfile.this, Profile.class));
//                finish();
//            }
//        });
//
//
//    }
//
//
//    private void showUser(String[] userData) {
//        username = userData[0];
//        dob = userData[1];
//        gender = userData[2];
//        height = userData[3];
//        weight = userData[4];
//        imageUrl = userData[5];
//        email = userData[6];
//
//        if (imageUrl != null) {
//            Glide.with(EditProfile.this).load(imageUrl).circleCrop().into(profilepic);
//        }
//        emailTV.setText(email);
//        usernameET.setText(username);
//        dobET.setText(dob);
//        genderET.setText(gender);
//        heightET.setText(height);
//        weightET.setText(weight);
//    }
//
//
//}


package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.fitfusion.myapplication.Model.UserProfile;
import com.fitfusion.myapplication.Model.Users; // Ensure this is the path to your Users model
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EditProfile extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String username, dob, gender, height,weight, email;
    private EditText usernameET, dobET, genderET, heightET, weightET;
    private TextView emailTV;
    private ImageView profilePic;
    private Uri imageUri;
    private FirebaseAuth authProfile;
    private String[] userData;
    private String imageUrl = ""; // Initialize to an empty string or null based on your logic
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
        setContentView(R.layout.activity_edit_profile);
        userData = getIntent().getStringArrayExtra("UserPassData");

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser fbUser = authProfile.getCurrentUser();
        if (fbUser == null) {
            // Handle user not logged in
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.fitnessPg:
//                    // Intent to go to Home Activity
//                    startActivity(new Intent(EditProfile.this, Planner.class));
//                    return true;
//                case R.id.foodPg:
//                    startActivity(new Intent(EditProfile.this, Blog.class));
//
//                    return true;
//                case R.id.blogPg:
//                    // Intent to go to Notifications Activity
//                    startActivity(new Intent(EditProfile.this, Blog.class));
//                    return true;
//
//                case R.id.profilePg:
//                    startActivity(new Intent(EditProfile.this, Profile.class));
//                    return true;
//
//                case R.id.homePg:
//                    startActivity(new Intent(EditProfile.this, MainActivity.class));
//                    return true;
//            }
//            return false;
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
                        startActivity(new Intent(EditProfile.this, Planner.class));
                        return true;
                    case R.id.foodPg:
                        // Intent to go to Dashboard Activity
                        startActivity(new Intent(EditProfile.this, Planner.class));
                        return true;
                    case R.id.blogPg:
                        Intent intent = new Intent(EditProfile.this, Blog.class);
                        startActivity(intent);
                        return true;

                    case R.id.profilePg:
                        startActivity(new Intent(EditProfile.this, Profile.class));
                        return true;

                    case R.id.homePg:
                        startActivity(new Intent(EditProfile.this, MainActivity.class));
                        return true;

                    case R.id.signout:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(EditProfile.this, "Logged Out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditProfile.this, Login.class));
//                        finish();
                        return true;
                }
                return false;
            }
        });

        showUser(userData);

        findViewById(R.id.changeProfileImageBtn).setOnClickListener(view -> openFileChooser());
        findViewById(R.id.changeProfileBtn).setOnClickListener(view -> updateProfile(fbUser));
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

    private void initViews() {
        profilePic = findViewById(R.id.profilepic);
        usernameET = findViewById(R.id.usernameET);
        dobET = findViewById(R.id.dobET);
        genderET = findViewById(R.id.genderET);
        heightET = findViewById(R.id.heightET);
        weightET = findViewById(R.id.weightET);
        emailTV = findViewById(R.id.emailTV);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadImage();
        }
    }

    private void uploadImage() {
        FirebaseUser fbUser = authProfile.getCurrentUser();
        if (fbUser != null && imageUri != null) {
            // Correctly initialized Firebase Storage reference
            StorageReference fileRef = FirebaseStorage.getInstance().getReference("ProfileImages").child(fbUser.getUid() + ".jpg");
            fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    imageUrl = uri.toString();
                    Glide.with(this).load(imageUri).into(profilePic);
                });
            }).addOnFailureListener(e -> Toast.makeText(EditProfile.this, "Image Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void updateProfile(FirebaseUser fbUser) {
        String username = usernameET.getText().toString().trim();
        String dob = dobET.getText().toString().trim();
        String gender = genderET.getText().toString().trim();
        String height = heightET.getText().toString().trim();
        String weight = weightET.getText().toString().trim();

        if (username.isEmpty() || dob.isEmpty() || gender.isEmpty() || height.isEmpty() || weight.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfile user = new UserProfile(username, email, gender, dob, height, weight, imageUrl);
        FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app").getReference("Registered Users")
                .child(fbUser.getUid())
                .setValue(user)
                .addOnCompleteListener((Task<Void> task) -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        updateWeightRecords(fbUser.getUid(), weight);
                        startActivity(new Intent(EditProfile.this, Profile.class));
                        finish();
                    } else {
                        Toast.makeText(EditProfile.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateWeightRecords(String userId, String newWeight) {
        // Getting the current timestamp
        long timestamp = System.currentTimeMillis();

        // Adding the new weight record with timestamp
        FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("UserRecords")
                .child(userId)
                .child("weight")
                .child(String.valueOf(timestamp))
                .setValue(newWeight)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditProfile.this, "Weight record updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditProfile.this, "Failed to update weight record", Toast.LENGTH_SHORT).show();
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
            Glide.with(EditProfile.this).load(imageUrl).circleCrop().into(profilePic);
        }
        emailTV.setText(email);
        usernameET.setText(username);
        dobET.setText(dob);
        genderET.setText(gender);
        heightET.setText(height);
        weightET.setText(weight);
    }
}
