package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.fitnessPg:
                    // Intent to go to Home Activity
                    startActivity(new Intent(MainActivity.this, Planner.class));
                    return true;
                case R.id.foodPg:
                    // Intent to go to Dashboard Activity
                    startActivity(new Intent(MainActivity.this, Planner.class));
                    return true;
                case R.id.blogPg:

                    Intent intent = new Intent(this, Blog.class);
                    startActivity(intent);
                    return true;

                case R.id.profilePg:
                    startActivity(new Intent(MainActivity.this, Profile.class));
                    return true;

                case R.id.homePg:
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    return true;
            }
            return false;
        });


    }

}