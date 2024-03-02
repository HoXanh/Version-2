package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    Button btn1, btn2, btn3;
    FloatingActionButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        btn = findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
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
                    // Intent to go to Dashboard Activity
                    startActivity(new Intent(Planner.this, Planner.class));
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

            parentLayout.addView(view);

            button.setOnClickListener(v -> {
                // Determine which activity to start based on some attribute of the plan
                // For example, using the plan title
                Intent intent;
                if ("Losing Weight".equals(plan.getTitle()) && "Beginner".equals(plan.getLevel())) {
                    intent = new Intent(Planner.this, SecondActivity.class);
                } else if ("Gain Muscle".equals(plan.getTitle()) && "Intermediate".equals(plan.getLevel())){
                    intent = new Intent(Planner.this, SecondActivity2.class);
                }
                else {
                    intent = new Intent(Planner.this, SecondActivity3.class); // Default or another condition
                }
                startActivity(intent);
            });


        }

    }



    public void LosingWeight(View view){
        Intent intent = new Intent(Planner.this, SecondActivity.class);
        startActivity(intent);
    }
    public void GainMuscle(View view) {
        Intent intent = new Intent(Planner.this, SecondActivity2.class);
        startActivity(intent);
    }

    public void GainStrength(View view) {
        Intent intent = new Intent(Planner.this, SecondActivity3.class);
        startActivity(intent);
    }
}