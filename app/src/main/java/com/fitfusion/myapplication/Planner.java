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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Planner extends AppCompatActivity {
    Button btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
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



//        Toolbar toolbar = findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);


//        btn1 = findViewById(R.id.FitnessPlanBtn1);
//        btn2 = findViewById(R.id.FitnessPlanBtn2);
//        btn3 = findViewById(R.id.FitnessPlanBtn3);

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Planner.this, SecondActivity.class);
//                startActivity(intent);
//            }
//        });
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Planner.this, SecondActivity2.class);
//                startActivity(intent);
//            }
//        });
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Planner.this, SecondActivity3.class);
//                startActivity(intent);
//            }
//        });

    }

//    private void setSupportActionBar(Toolbar toolbar) {
//    }

//    public void setSupportActionBar(Toolbar toolbar){
//
//    }

    public void populateViews(List<FitnessPlan> fitnessPlans) {
        LinearLayout parentLayout = findViewById(R.id.parentLayout); // Ensure this ID matches your layout
        LayoutInflater inflater = LayoutInflater.from(this);

        for (FitnessPlan plan : fitnessPlans) {
            View view = inflater.inflate(R.layout.item_fitness_plan, parentLayout, false);

            TextView title = view.findViewById(R.id.textViewTitle);
            TextView level = view.findViewById(R.id.textViewLevel);
            TextView duration = view.findViewById(R.id.textViewDuration);
            // Corrected: Find imageView within the inflated 'view'
            ImageView imageView = view.findViewById(R.id.imageViewPlanImage);
            String imageUrl = plan.getImage();

            title.setText(plan.getTitle());
            level.setText(plan.getLevel());
            duration.setText(plan.getDuration());

            Glide.with(view.getContext()) // Use the context of the inflated view
                    .load(imageUrl)
                    .into(imageView);

            parentLayout.addView(view);
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