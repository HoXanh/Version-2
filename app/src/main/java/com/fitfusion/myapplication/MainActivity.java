package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fitfusion.myapplication.Model.FitnessPlan;
import com.fitfusion.myapplication.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TextView text;
    private ListView listView;
    ActivityMainBinding binding;
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseReference myRef = FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app/").getReference("fitness_plans");

        ArrayList<String> list = new ArrayList<>();
//        text = findViewById(R.id.textViewImageUrl);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    FitnessPlan info = snapshot1.getValue(FitnessPlan.class);
                    String txt = info.getImage();

//                    snapshot1.getValue().toString()
                }
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/esp-g13-trainify.appspot.com/o/planpics%2Flosingweight.png?alt=media&token=e5ebe21c-deae-48cd-9db3-dfe30971079e";

//        text.setText(imageUrl);
        ImageView imageView = findViewById(R.id.imageView); // Replace with your actual ImageView ID
        Glide.with(this)
                .load(imageUrl)
                .into(imageView);






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
                    // Intent to go to Notifications Activity
                    startActivity(new Intent(MainActivity.this, Blog.class));
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
    private void loadImageIntoImageView(String imageUrl) {
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this)
                .load(imageUrl)
                .into(imageView);
    }


}