package com.fitfusion.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String receivedNumber = getIntent().getStringExtra("NUMBER_KEY");

        Log.d("Blog", "Received Number: " + receivedNumber);

    }
}