package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fitfusion.myapplication.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistryActivity extends AppCompatActivity {

    private EditText username,email, password, dob, height, weight;

    private Button register;
    private RadioGroup genderChoice;
    private RadioButton genderSelected;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        dob = findViewById(R.id.dob);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);

        register = findViewById(R.id.register);
        genderChoice = findViewById(R.id.gender);
        genderChoice.clearCheck();

        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int genderSelectedID = genderChoice.getCheckedRadioButtonId();
                genderSelected = findViewById(genderSelectedID);

                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_dob = dob.getText().toString();
                String txt_height= height.getText().toString();
                String txt_weight = weight.getText().toString();
                String txt_gender;

                if (txt_password.isEmpty() || txt_email.isEmpty()){
                    Toast.makeText(RegistryActivity.this, "Empty Credential!!!", Toast.LENGTH_SHORT).show();
                } else if  (txt_password.length() < 6){
                    Toast.makeText(RegistryActivity.this, "Password is too Short.", Toast.LENGTH_SHORT).show();
                } else {
                    txt_gender = genderSelected.getText().toString();
                    registerUser(txt_username, txt_email , txt_password, txt_gender, txt_dob, txt_height, txt_weight);
                }
            }
        });
    }

    private void registerUser(String username,String email, String password, String gender, String dob, String height, String weight) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistryActivity.this,new  OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (task.isSuccessful()) {
                        FirebaseUser fbUser = auth.getCurrentUser();


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
                    Toast.makeText(RegistryActivity.this, "Registering Failed!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}