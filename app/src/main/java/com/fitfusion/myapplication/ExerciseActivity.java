package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fitfusion.myapplication.Model.BlogPost;
import com.fitfusion.myapplication.Model.Day;
import com.fitfusion.myapplication.Model.Exercise;
import com.fitfusion.myapplication.Model.FitnessPlan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {
//    FloatingActionButton homeBtn;
DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        int planId  = getIntent().getIntExtra("NUMBER_KEY", 1);
        Log.d("SecondActivity", "Received Number: " + planId);
//        homeBtn = findViewById(R.id.fab);
//        homeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ExerciseActivity.this, MainActivity.class));
//            }
//        });
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.fitnessPg:
                        // Intent to go to Home Activity
                        startActivity(new Intent(ExerciseActivity.this, Planner.class));
                        return true;
                    case R.id.foodPg:
                        // Intent to go to Dashboard Activity
                        startActivity(new Intent(ExerciseActivity.this, Planner.class));
                        return true;
                    case R.id.blogPg:
                        Intent intent = new Intent(ExerciseActivity.this, Blog.class);
                        startActivity(intent);
                        return true;

                    case R.id.profilePg:
                        startActivity(new Intent(ExerciseActivity.this, Profile.class));
                        return true;

                    case R.id.homePg:
                        startActivity(new Intent(ExerciseActivity.this, MainActivity.class));
                        return true;

                    case R.id.signout:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(ExerciseActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ExerciseActivity.this, Login.class));
//                        finish();
                        return true;
                }
                return false;
            }
        });

// Assuming 'inputId' is the ID you are looking for

//        databaseReference.orderByChild("id").equalTo(planId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    FitnessPlan plan = snapshot.getValue(FitnessPlan.class);
//                    // Now 'plan' contains the fetched FitnessPlan object
//                    // Implement your logic here, e.g., update the UI
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w("SecondActivity2", "loadPost:onCancelled", databaseError.toException());
//            }
//        });

        LinearLayout mainLayout = findViewById(R.id.mainLinearLayout);
        LayoutInflater inflater = LayoutInflater.from(this);
        DatabaseReference myRef = FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app/").getReference("fitness_plans");

        myRef.orderByChild("id").equalTo(planId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FitnessPlan plan = snapshot.getValue(FitnessPlan.class);
                        TextView description = findViewById(R.id.workoutDescription);


                        if (planId % 3 == 0) {

                            for (String btn : plan.getButtons()){
                                LinearLayout containerLayout = findViewById(R.id.containerLayout);
                                LayoutInflater inflater = LayoutInflater.from(containerLayout.getContext());
                                View buttonView = inflater.inflate(R.layout.button, containerLayout, false);
                                Button btns = buttonView.findViewById(R.id.myButton);
                                btns.setText(btn.substring(20));
                                Log.d("ExerciseActivity", btn.substring(20));
                                containerLayout.addView(btns);

                                btns.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String id = btn.substring(0,20);
                                        Intent intent = new Intent(ExerciseActivity.this, ReadBlog.class);
                                        intent.putExtra("NUMBER_KEY", id);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                        description.setText(plan.getDescription());
                        // Assuming FitnessPlan class has a structure to hold days and exercises
                        for (Day day : plan.getDays()) {
                            // Inflate the day layout
                            View dayView = inflater.inflate(R.layout.day_layout, mainLayout, false);
                            TextView dayTitle = dayView.findViewById(R.id.textViewDayTitle);

                            dayTitle.setText(day.getDay());
                            mainLayout.addView(dayView); // Add day view to the main layout

                            for (Exercise exercise : day.getExercises()) {
                                View exerciseView = inflater.inflate(R.layout.exercise_layout, mainLayout, false);
                                TextView exerciseName = exerciseView.findViewById(R.id.textViewExerciseName);
                                TextView exerciseReps = exerciseView.findViewById(R.id.textViewReps);
                                ImageView exerciseImage = exerciseView.findViewById(R.id.imageViewExercise);
                                String exName = exercise.getName();
                                String result = "exercises/" + exName.toLowerCase().replace(" ", "") + ".png";
                                FirebaseStorage storage = FirebaseStorage.getInstance("gs://esp-g13-trainify.appspot.com");
                                StorageReference pathReference = storage.getReference().child(result);;
                                pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Glide.with(getApplicationContext())
                                                .load(uri.toString())
                                                .into(exerciseImage);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                    }
                                });
                                // Inflate the exercise layout

                                // Set the exercise details
                                exerciseName.setText(exercise.getName());
                                exerciseReps.setText(exercise.getReps());

//                                // Use Glide to load the exercise image
//                                Glide.with(exerciseView.getContext())
//                                        .load(exercise.imageUrl) // Assuming Exercise class has an imageUrl field
//                                        .into(exerciseImage);

                                mainLayout.addView(exerciseView); // Add the exercise view to the exercises layout
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ExerciseActivity", "loadPost:onCancelled", databaseError.toException());
            }
        });
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.fitnessPg:
//                    // Intent to go to Home Activity
//                    startActivity(new Intent(ExerciseActivity.this, Planner.class));
//                    return true;
//                case R.id.foodPg:
//                    startActivity(new Intent(ExerciseActivity.this, Blog.class));
//
//                    return true;
//                case R.id.blogPg:
//                    // Intent to go to Notifications Activity
//                    startActivity(new Intent(ExerciseActivity.this, Blog.class));
//                    return true;
//
//                case R.id.profilePg:
//                    startActivity(new Intent(ExerciseActivity.this, Profile.class));
//                    return true;
//
//                case R.id.homePg:
//                    startActivity(new Intent(ExerciseActivity.this, MainActivity.class));
//                    return true;
//            }
//            return false;
//        });



// Inflate the day layout
//        View dayView = inflater.inflate(R.layout.day_layout, mainLayout, false);
//        TextView dayTitle = dayView.findViewById(R.id.textViewDayTitle);
//        LinearLayout exercisesLayout = dayView.findViewById(R.id.exercisesLinearLayout);

// Set the day's title
//        dayTitle.setText("Day 1");
//
//// Add the inflated day layout to the main layout
//        mainLayout.addView(dayView);
//        for (int i = 0; i < 2; i++){
//            View exerciseView = inflater.inflate(R.layout.exercise_layout, mainLayout, false);
//            TextView exerciseName = exerciseView.findViewById(R.id.textViewExerciseName);
//            TextView exerciseReps = exerciseView.findViewById(R.id.textViewReps);
//            ImageView exerciseImage = exerciseView.findViewById(R.id.imageViewExercise);
//            String url = "https://firebasestorage.googleapis.com/v0/b/esp-g13-trainify.appspot.com/o/exercises%2Fdeadlift.png?alt=media&token=f2a8e6e6-ea57-444e-9b41-c2cd7a938faf";
//// Set the exercise details
//            exerciseName.setText("Deadlift");
//            exerciseReps.setText("5x5");
//            Glide.with(exerciseView.getContext()) // Use the context of the inflated view
//                    .load(url)
//                    .into(exerciseImage);
//
//// Add the exercise view to the exercises layout
//            mainLayout.addView(exerciseView);
//        }


// Repeat the exercise inflation and addition process for each exercise in the day


//        FitnessPlan workoutPlan = fetchWorkoutPlanSync(planId);


//
//        for (Day day : workoutPlan.getDays()) {
//            // Inflate the day layout
//            View dayView = LayoutInflater.from(this).inflate(R.layout.day_layout, mainLayout, false);
//            TextView dayTitle = dayView.findViewById(R.id.textViewDayTitle);
//            LinearLayout exercisesLayout = dayView.findViewById(R.id.exercisesLinearLayout);

//            dayTitle.setText(day.getDay());
//
//            for (Exercise exercise : day.getExercises()) {
//                // Inflate the exercise layout
//                View exerciseView = LayoutInflater.from(this).inflate(R.layout.exercise_layout, exercisesLayout, false);
//                TextView exerciseName = exerciseView.findViewById(R.id.textViewExerciseName);
//                TextView exerciseReps = exerciseView.findViewById(R.id.textViewReps);
//                 ImageView exerciseImage = exerciseView.findViewById(R.id.imageViewExercise);
//                 String exName = exercise.getName();
//                 String result = "exercises/" + exName.toLowerCase().replace(" ", "") + ".png";
//                FirebaseStorage storage = FirebaseStorage.getInstance("gs://esp-g13-trainify.appspot.com");
//                StorageReference pathReference = storage.getReference().child(result);;
//                pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Glide.with(getApplicationContext())
//                                .load(uri.toString())
//                                .into(exerciseImage);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle any errors
//                    }
//                });


//                exerciseName.setText(exercise.getName());
//                exerciseReps.setText(exercise.getInfo());
//
//                exercisesLayout.addView(exerciseView);
//            }

//            mainLayout.addView(dayView);
//        }

    }
    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer((GravityCompat.START));
        }
        else {
            super.onBackPressed();
        }

    }

}
//
//
