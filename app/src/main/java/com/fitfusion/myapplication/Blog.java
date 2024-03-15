package com.fitfusion.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Blog extends AppCompatActivity {
    private Button newBlogBtn;

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        newBlogBtn = findViewById(R.id.createBlogButton);
        newBlogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Blog.this, AddBlog.class));
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.fitnessPg:
                    // Intent to go to Home Activity
                    startActivity(new Intent(Blog.this, Planner.class));
                    return true;
                case R.id.foodPg:
                    startActivity(new Intent(Blog.this, Blog.class));

                    return true;
                case R.id.blogPg:
                    // Intent to go to Notifications Activity
                    startActivity(new Intent(Blog.this, Blog.class));
                    return true;

                case R.id.profilePg:
                    startActivity(new Intent(Blog.this, Profile.class));
                    return true;

                case R.id.homePg:
                    startActivity(new Intent(Blog.this, MainActivity.class));
                    return true;
            }
            return false;
        });


    }
}