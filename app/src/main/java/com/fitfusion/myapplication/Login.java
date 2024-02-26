package com.fitfusion.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText inputUsername, inputPassword;
    private Button loginButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";
    private CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUIView();
    }

    private void setupUIView() {
        inputUsername = findViewById(R.id.username);
        //inputUsername.setFilters(new InputFilter[]{new TextInputFilter(20)});
        inputPassword = findViewById(R.id.password);
        //inputPassword.setFilters(new InputFilter[]{new TextInputFilter(20)});
        checkBoxRememberMe = findViewById(R.id.remember_me_chk);
        loginButton = findViewById(R.id.log_in_button);
        //Paper.init(this);
        loadingBar = new ProgressDialog(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { LoginUser(); }
        });
    }

    private void LoginUser(){
        String name=inputUsername.getText().toString();
        String password=inputPassword.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter your username...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();
        }else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait. We are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

//            CheckCredentials(name,password);
        }
    }

    private void CheckCredentials(String name, String password){


    }
}