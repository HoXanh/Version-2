package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fitfusion.myapplication.Model.Day;
import com.fitfusion.myapplication.Model.Exercise;
import com.fitfusion.myapplication.Model.FitnessPlan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SecondActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
        int planId  = getIntent().getIntExtra("NUMBER_KEY", 1);
        Log.d("SecondActivity", "Received Number: " + planId);

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
                Log.w("SecondActivity2", "loadPost:onCancelled", databaseError.toException());
            }
        });




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

}
//
//
