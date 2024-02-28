package com.fitfusion.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Planner extends AppCompatActivity {
    Button btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);


        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);


        btn1 = findViewById(R.id.FitnessPlanBtn1);
        btn2 = findViewById(R.id.FitnessPlanBtn2);
        btn3 = findViewById(R.id.FitnessPlanBtn3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Planner.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Planner.this, SecondActivity2.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Planner.this, SecondActivity3.class);
                startActivity(intent);
            }
        });

    }

//    private void setSupportActionBar(Toolbar toolbar) {
//    }

//    public void setSupportActionBar(Toolbar toolbar){
//
//    }

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