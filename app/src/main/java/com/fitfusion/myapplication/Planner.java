package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fitfusion.myapplication.Model.FitnessPlan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Planner extends AppCompatActivity {

    FloatingActionButton homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        homeBtn = findViewById(R.id.fab);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Planner.this, MainActivity.class));
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app/").getReference("fitness_plans");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FitnessPlan> fitnessPlans = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FitnessPlan plan = snapshot.getValue(FitnessPlan.class);
                    fitnessPlans.add(plan);
                }
                populateViews(fitnessPlans);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.fitnessPg:
                    // Intent to go to Home Activity
                    startActivity(new Intent(Planner.this, Planner.class));
                    return true;
                case R.id.foodPg:
                    startActivity(new Intent(Planner.this, Blog.class));

                    return true;
                case R.id.blogPg:
                    // Intent to go to Notifications Activity
                    startActivity(new Intent(Planner.this, Blog.class));
                    return true;

                case R.id.profilePg:
                    startActivity(new Intent(Planner.this, Profile.class));
                    return true;

                case R.id.homePg:
                    startActivity(new Intent(Planner.this, MainActivity.class));
                    return true;
            }
            return false;
        });
    }

    ArrayList<String> list = new ArrayList<>();

    public void populateViews(List<FitnessPlan> fitnessPlans) {
        LinearLayout parentLayout = findViewById(R.id.parentLayout); // Ensure this ID matches your layout
        LayoutInflater inflater = LayoutInflater.from(this);

        for (FitnessPlan plan : fitnessPlans) {
            View view = inflater.inflate(R.layout.item_fitness_plan, parentLayout, false);

            TextView title = view.findViewById(R.id.textViewTitle);
            TextView level = view.findViewById(R.id.textViewLevel);
            TextView duration = view.findViewById(R.id.textViewDuration);
            ImageView imageView = view.findViewById(R.id.imageViewPlanImage);
            Button button = view.findViewById(R.id.buttonGetStarted);
            String imageUrl = plan.getImage();
            list.add(plan.getTitle());

            title.setText(plan.getTitle());
            level.setText(plan.getLevel());
            duration.setText(plan.getDuration());

            Glide.with(view.getContext()) // Use the context of the inflated view
                    .load(imageUrl)
                    .into(imageView);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int planId = plan.getId(); // Retrieving the tag
                    Intent intent = new Intent(Planner.this, ExerciseActivity.class);
                    intent.putExtra("NUMBER_KEY", planId);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Plan clicked: " + plan.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });


            parentLayout.addView(view);

        }
    }
}


