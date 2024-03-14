package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
//axaxa

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth authProfile;
    private LineChart chart;

    private List<Entry> weightEntries = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = findViewById(R.id.weightChart);

        fetchdata();

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
                    Intent intent = new Intent(this, Blog.class);
                    startActivity(intent);
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

    private void fetchdata(){

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser fbUser = authProfile.getCurrentUser();


        FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app").getReference("UserRecords").child(fbUser.getUid()).child("weight_records").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                weightEntries.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String date = snapshot.getKey();
                    float weight = Float.parseFloat(snapshot.getValue(String.class));
                    try {
                        // Convert date string to timestamp
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
                        long timestamp = sdf.parse(date).getTime();
                        weightEntries.add(new Entry(timestamp, weight));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                updateChart();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }


    private void updateChart() {
        Description description = new Description();
        description.setText("Weight Record");
        description.setPosition(150f, 15f);
        chart.setDescription(description);
        chart.getAxisRight().setDrawLabels(false);

        List<Entry> indexedEntries = new ArrayList<>();
        final List<String> dateLabels = new ArrayList<>();
        float totalWeight = 0;
        String timestampAsString = "1710441014884";

        // Assuming weightEntries is already populated with your data
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
        SimpleDateFormat labelFormat = new SimpleDateFormat("dd/MM/yy", Locale.US);

        for (int i = 0; i < weightEntries.size(); i++) {
            float weight = weightEntries.get(i).getY();
            totalWeight += weight;

            long timestamp = (long) weightEntries.get(i).getX();
            Date date = new Date(timestamp);

            // Use index as X value for equal spacing on the chart
            indexedEntries.add(new Entry(i, weight));

            // Assuming the timestamp represents a date, convert it back to the readable format
            String dateString = labelFormat.format(date);
            dateLabels.add(dateString);
        }


        // Calculate mean weight
        float meanWeight = totalWeight / weightEntries.size();

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Ensure the index is within the bounds of the dateLabels list
                int index = (int)value;
                if(index >= 0 && index < dateLabels.size()) {
                    return dateLabels.get(index);
                }
                return ""; // Return an empty string to avoid index out of bounds error
            }
        });

        xAxis.setGranularity(1f); // Ensure each index has a label
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setAxisLineWidth(2f);
        xAxis.setLabelCount(Math.min(dateLabels.size(), 5)); // Limit the number of labels to the smaller of 5 or the size of dateLabels


        YAxis yAxis = chart.getAxisLeft();
        // Set Y-axis bounds based on mean weight ± 10kg
        yAxis.setAxisMinimum(meanWeight - 10);
        yAxis.setAxisMaximum(meanWeight + 10);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(4);

        LineDataSet dataSet = new LineDataSet(indexedEntries, "Weight");
        dataSet.setColor(Color.BLUE);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }




}