package com.fitfusion.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadBlog extends AppCompatActivity {

    private String title;
    private String description;
    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read_blog);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String postID = getIntent().getStringExtra("NUMBER_KEY");
        Log.d("ReadBlog", "Received blog ID: " + postID);

        if (postID.charAt(0) == '0'){

        }

        DatabaseReference blogRoot = FirebaseDatabase.getInstance(Blog.DATABASE_URL)
                .getReference()
                .child("Blogs")
                .child(postID);
        blogRoot.child("title").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        title = snapshot.getValue(String.class);
                        ((TextView)findViewById(R.id.blogTitle)).setText(title);
                        Log.d("ReadBlog", "Received title: " + title);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });

        blogRoot.child("description").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                description = snapshot.getValue(String.class);
                ((TextView)findViewById(R.id.blogDescription)).setText(description);
                Log.d("ReadBlog", "Received description: " + description);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        blogRoot.child("imageUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imageUrl = snapshot.getValue(String.class);
                ImageView imageView = findViewById(R.id.blogImage);
                Glide.with(ReadBlog.this).load(imageUrl).into(imageView);
                Log.d("ReadBlog", "Received image URL: " + imageUrl);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        Button button = findViewById(R.id.blogBackButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadBlog.this, Blog.class);
                startActivity(intent);
                finish();
            }
        });
    }
}