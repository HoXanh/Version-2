package com.fitfusion.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    private Button plannerBtn;
    int[] newArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        plannerBtn = findViewById(R.id.plannerBtn);
        plannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, Planner.class);
                startActivity(intent);
                finish();

            }
        });


        newArray = new int[]{
                R.id.march, R.id.legswing, R.id.armswing, R.id.squat, R.id.dynamic, R.id.squat2, R.id.pushup, R.id.mountainclimbers,
                R.id.lunges, R.id.plank, R.id.buttkicks, R.id.highknee, R.id.armsprint
        };
        
    }

    public void Imagebtnclicked(View view) {
        for(int i = 0; i < newArray.length; i++){

        }
    }
}