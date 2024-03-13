package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fitfusion.myapplication.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//public class RegistryActivity extends AppCompatActivity {
//
//    private EditText username,email, password, dob, height, weight;
//    private Button register;
//    private RadioGroup genderChoice;
//    private RadioButton genderSelected;
//
//    private static final int PICK_IMAGE_REQUEST = 1;
//    private Uri imageUri;
//    private FirebaseAuth auth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registry);
//
//        username = findViewById(R.id.username);
//        email = findViewById(R.id.email);
//        password = findViewById(R.id.password);
//
//        dob = findViewById(R.id.dob);
//        height = findViewById(R.id.height);
//        weight = findViewById(R.id.weight);
//
//
//        register = findViewById(R.id.register);
//        genderChoice = findViewById(R.id.gender);
//        genderChoice.clearCheck();
//
//        auth = FirebaseAuth.getInstance();
//
////        Button selectImageButton = findViewById(R.id.select_image_btn);
////        selectImageButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                openFileChooser();
////            }
////        });
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int genderSelectedID = genderChoice.getCheckedRadioButtonId();
//                genderSelected = findViewById(genderSelectedID);
//
//                String txt_username = username.getText().toString();
//                String txt_email = email.getText().toString();
//                String txt_password = password.getText().toString();
//                String txt_dob = dob.getText().toString();
//                String txt_height= height.getText().toString();
//                String txt_weight = weight.getText().toString();
//                String txt_gender;
//
//
//                if (txt_password.isEmpty() || txt_email.isEmpty()){
//                    Toast.makeText(RegistryActivity.this, "Empty Credential!!!", Toast.LENGTH_SHORT).show();
//                } else if  (txt_password.length() < 6){
//                    Toast.makeText(RegistryActivity.this, "Password is too Short.", Toast.LENGTH_SHORT).show();
//                } else {
//                    txt_gender = genderSelected.getText().toString();
//                    registerUser(txt_username, txt_email , txt_password, txt_gender, txt_dob, txt_height, txt_weight);
//                }
//            }
//        });
//    }
//
//
//    private void registerUser(String username,String email, String password, String gender, String dob, String height, String weight) {
//        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistryActivity.this,new  OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    if (task.isSuccessful()) {
//                        FirebaseUser fbUser = auth.getCurrentUser();
//
//
//                        Users writeUserDetails = new Users(username,email, gender, dob, height, weight);
//                        //https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app - Putting full URL for Database refProfile to solve the unmatch region bugs
//                        DatabaseReference refProfile = FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app").getReference("Registered Users");
//                        refProfile.child(fbUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(RegistryActivity.this, "Registering User Successful", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(RegistryActivity.this, Login.class));
//                                finish();
//                            }
//                        });
//                    }
//                } else {
//                    Toast.makeText(RegistryActivity.this, "Registering Failed!!!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//}

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fitfusion.myapplication.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistryActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText username, email, password, dob, height, weight;
    private Button register, selectImageButton;
    private RadioGroup genderChoice;
    private RadioButton genderSelected;
    private FirebaseAuth auth;
    private ImageView profileImage;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        // Initialize UI components
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        dob = findViewById(R.id.dob);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        register = findViewById(R.id.register);
        genderChoice = findViewById(R.id.gender);
        profileImage = findViewById(R.id.profile_image);
        selectImageButton = findViewById(R.id.select_image_btn);

        auth = FirebaseAuth.getInstance();

        // Clear radio group selection
        genderChoice.clearCheck();

        selectImageButton.setOnClickListener(v -> openFileChooser());

        register.setOnClickListener(v -> {
            int genderSelectedID = genderChoice.getCheckedRadioButtonId();
            genderSelected = findViewById(genderSelectedID);

            String txt_username = username.getText().toString().trim();
            String txt_email = email.getText().toString().trim();
            String txt_password = password.getText().toString().trim();
            String txt_dob = dob.getText().toString().trim();
            String txt_height = height.getText().toString().trim();
            String txt_weight = weight.getText().toString().trim();
            String txt_gender = genderSelected.getText().toString().trim();
            Pattern EMAIL_PATTERN = 
            Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

            StringBuilder errorMessage = new StringBuilder();
    

            if (txt_username.isEmpty()) errorMessage.append("Username is required. ");
            if (txt_email.isEmpty() || !isValidEmail(txt_email) errorMessage.append("Valid email is required. ");
            if (txt_password.isEmpty() || txt_password.length() < 6) errorMessage.append("A stronger password with length greater than 6 is required. ");
            if (txt_dob.isEmpty()) errorMessage.append("Date of Birth is required. ");
            if (txt_height.isEmpty()) errorMessage.append("Height is required. ");
            if (txt_weight.isEmpty()) errorMessage.append("Weight is required. ");
            if (txt_gender.isEmpty()) errorMessage.append("Gender is required. ");
    
            // If there are any error messages, show them in a Toast; otherwise, proceed with user registration
            if (errorMessage.length() > 0) {
                Toast.makeText(YourActivity.this, errorMessage.toString(), Toast.LENGTH_LONG).show();
            } else {
                registerUser(txt_username, txt_email, txt_password, txt_gender, txt_dob, txt_height, txt_weight);
            }
            
            
        });
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
            profileImage.setImageURI(imageUri);
        }
    }

    public boolean isValidEmail(String email) {
        if (email.equals(null)){
            return false;
        }
        Pattern EMAIL_PATTERN = 
            Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private void registerUser(String username, String email, String password, String gender, String dob, String height, String weight) {


        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistryActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser fbUser = auth.getCurrentUser();
                    uploadImage(fbUser, new Users(username, email, gender, dob, height, weight)); // Adjusted to include image upload
                } else {
                    Toast.makeText(RegistryActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadImage(FirebaseUser fbUser, Users user) {
        if (imageUri != null) {
            StorageReference fileReference = FirebaseStorage.getInstance().getReference("uploads").child(fbUser.getUid() + ".jpg");

            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        user.setImageUrl(imageUrl); // Assuming Users model has an imageUrl field setter
                        saveUserDetails(fbUser, user);
                    });
                }
            }).addOnFailureListener(e -> Toast.makeText(RegistryActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            // If no image was selected, proceed to save other details
            saveUserDetails(fbUser, user);
        }
    }

    private void saveUserDetails(FirebaseUser fbUser, Users user) {
        DatabaseReference refProfile = FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app").getReference("Registered Users");
        refProfile.child(fbUser.getUid()).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(RegistryActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistryActivity.this, Login.class));
                finish();
            } else {
                Toast.makeText(RegistryActivity.this, "Failed to register: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void hello(String username,String email, String password, String gender, String dob, String height, String weight) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistryActivity.this,new  OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (task.isSuccessful()) {
                        FirebaseUser fbUser = auth.getCurrentUser();
                        uploadImage(fbUser, new Users(username, email, gender, dob, height, weight));


                        Users writeUserDetails = new Users(username,email, gender, dob, height, weight);
                        //https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app - Putting full URL for Database refProfile to solve the unmatch region bugs
                        DatabaseReference refProfile = FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app").getReference("Registered Users");
                        refProfile.child(fbUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RegistryActivity.this, "Registering User Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistryActivity.this, Login.class));
                                finish();
                            }
                        });
                    }
                } else {
                    Toast.makeText(RegistryActivity.this, "Registering Failed!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
