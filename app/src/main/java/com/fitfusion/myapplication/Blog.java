package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fitfusion.myapplication.Model.BlogPost;
import com.fitfusion.myapplication.Model.FitnessPlan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Blog extends AppCompatActivity {

    public static final String DATABASE_URL =
            "https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app";
    public static final String BLOGS_TABLE_NAME = "Blogs";

    private Button newBlogBtn;

    FloatingActionButton homeBtn;

    public List<BlogPost> allPost = new ArrayList<>();
    public List<BlogPost> displayedPost = new ArrayList<>();
    private List<BlogPost> posts;
    private BlogPostAdapter adapter;
    SearchView searchView;

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
//        searchView = findViewById(R.id.searchViewBlog);

        homeBtn = findViewById(R.id.fab);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Blog.this, MainActivity.class));
            }
        });

        newBlogBtn = findViewById(R.id.createBlogButton);
        newBlogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Blog.this, AddBlog.class));
                finish();
            }
        });
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filter(newText);
//                return false;
//            }
//        });

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

//        posts = new ArrayList<>();
        adapter = new BlogPostAdapter(this, displayedPost);
        ListView listView = findViewById(R.id.blogListView);
        listView.setAdapter(adapter);

        DatabaseReference ref = FirebaseDatabase
                .getInstance(DATABASE_URL)
                .getReference()
                .child("Blogs");

        ref.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                posts.clear();
//                for (DataSnapshot s : snapshot.getChildren()) {
//                    BlogPost post = s.getValue(BlogPost.class);
//                    if (post != null) {
//                        posts.add(post);
//                    }
//                }
//                Collections.reverse(posts);
//                adapter.notifyDataSetChanged();
                allPost.clear();
                displayedPost.clear();
//                List<FitnessPlan> fitnessPlans = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BlogPost post = snapshot.getValue(BlogPost.class);
                    if (post != null){
                        allPost.add(post);
                        displayedPost.add(post);
                    }
                }
                Collections.reverse(displayedPost);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


        public void filter(String text) {

        List<BlogPost> filteredList = new ArrayList<>();
        for (BlogPost post : allPost) {
            String planTitle = post.getTitle().toLowerCase();
            if (planTitle.contains(text.toLowerCase().trim())) {
                filteredList.add(post);
            }
        }
        displayedPost.clear();
        displayedPost.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }
}