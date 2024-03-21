package com.fitfusion.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fitfusion.myapplication.Model.MetricDataPoint;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth authProfile;

    private Map<String, Pair<LineChart, List<Entry>>> metricCharts = new HashMap<>();

    private LinearLayout metricsContainer;
    private TextView desc;
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    FloatingActionButton fab;
    private String FirebaseUrl;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.FirebaseUrl = "https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app";
        metricsContainer = findViewById(R.id.metricsContainer);

        fetchAllMetricsData();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showAddMetricDialog());


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

    private void fetchAllMetricsData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance(FirebaseUrl);
        DatabaseReference userMetricsRef = database.getReference("UserRecords").child(userId);
        userMetricsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot metricSnapshot : dataSnapshot.getChildren()) {
                    String metricName = metricSnapshot.getKey();
                    fetchDataForMetric(metricName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("fetchData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    private void showAddMetricDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Metric");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Metric description");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String metricDescription = input.getText().toString().trim();
            List<Entry> metricEntries = new ArrayList<>();
            if (!metricDescription.isEmpty()) {
                // You might want to validate the metric name or check for duplicates here
                addMetricCard(metricDescription, metricEntries); // Call addMetricCard with the entered description
            } else {
                Toast.makeText(getApplicationContext(), "Metric description cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

//    private void addMetricCard(String metricName, List<Entry> metricEntries) {
//        View cardView = LayoutInflater.from(this).inflate(R.layout.metric_card_layout, metricsContainer, false);
//
//        LineChart chart = cardView.findViewById(R.id.metricChart);
//        EditText etMetricValue = cardView.findViewById(R.id.etMetricValue);
//        TextView desc = cardView.findViewById(R.id.description);
//        View btnSubmitMetric = cardView.findViewById(R.id.btnSubmitMetric);
//
//        setupChart(chart, metricName, metricEntries); // Setup chart with a separate method
//
//        desc.setText(metricName + " records");
//        // Setup the submit button click listener
//        btnSubmitMetric.setOnClickListener(v -> {
//            String metricValueStr = etMetricValue.getText().toString();
//            if (!metricValueStr.isEmpty()) {
//                float metricValue = Float.parseFloat(metricValueStr);
//                // Here you would add the metric value to your database and refresh the chart
//                // For demonstration, assuming a method addDataToMetric which updates Firebase and refreshes the chart
//                addDataToMetric(metricName, metricValue, chart, metricEntries);
//            }
//        });
//
//        metricsContainer.addView(cardView);
//    }

    private void addMetricCard(String metricName, List<Entry> newEntries) {
        Pair<LineChart, List<Entry>> existing = metricCharts.get(metricName);

        if (existing != null) {
            // Metric already exists, update its data
            List<Entry> metricEntries = existing.second;
            metricEntries.clear();
            metricEntries.addAll(newEntries); // Update with new data

            LineChart chart = existing.first;
            setupChart(chart, metricName, metricEntries); // Refresh chart
        } else {
            // Metric doesn't exist, create new
            View cardView = LayoutInflater.from(this).inflate(R.layout.metric_card_layout, metricsContainer, false);

            LineChart chart = cardView.findViewById(R.id.metricChart);
            setupChart(chart, metricName, newEntries); // Initial setup
            EditText etMetricValue = cardView.findViewById(R.id.etMetricValue);
            TextView desc = cardView.findViewById(R.id.description);
            View btnSubmitMetric = cardView.findViewById(R.id.btnSubmitMetric);
            desc.setText(metricName + " records");
        // Setup the submit button click listener

            btnSubmitMetric.setOnClickListener(v -> {
                String metricValueStr = etMetricValue.getText().toString();
                if (!metricValueStr.isEmpty()) {
                    float metricValue = Float.parseFloat(metricValueStr);
                    // Here you would add the metric value to your database and refresh the chart
                    // For demonstration, assuming a method addDataToMetric which updates Firebase and refreshes the chart
                    addDataToMetric(metricName, metricValue, chart, newEntries);
                }
            });

            // Other UI setup code here...

            // Store the chart and entries for future updates
            metricCharts.put(metricName, new Pair<>(chart, newEntries));
            metricsContainer.addView(cardView);
        }
    }


    private void setupChart(LineChart chart, String metricName, List<Entry> metricEntries) {
        Description description = new Description();
        description.setText(metricName);
        description.setPosition(150f, 15f);
        chart.setDescription(description);
        chart.getAxisRight().setDrawLabels(false);

        List<Entry> indexedEntries = new ArrayList<>();
        final List<String> dateLabels = new ArrayList<>();

        // Assuming weightEntries is already populated with your data
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
        SimpleDateFormat labelFormat = new SimpleDateFormat("dd/MM/yy", Locale.US);
        float maxValue = Float.MIN_VALUE;
        float minValue = Float.MAX_VALUE;

        for (int i = 0; i < metricEntries.size(); i++) {
            float weight = metricEntries.get(i).getY();
            if (weight >= maxValue){
                maxValue = weight;
            }
            if (weight <= minValue){
                minValue = weight;
            }


            long timestamp = (long) metricEntries.get(i).getX();
            Date date = new Date(timestamp);

            // Use index as X value for equal spacing on the chart
            indexedEntries.add(new Entry(i, weight));

            // Assuming the timestamp represents a date, convert it back to the readable format
            String dateString = labelFormat.format(date);
            dateLabels.add(dateString);
        }

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
        yAxis.setAxisMinimum(minValue - 15);
        yAxis.setAxisMaximum(maxValue + 15);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(4);

        LineDataSet dataSet = new LineDataSet(indexedEntries, metricName);
        dataSet.setColor(Color.BLUE);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }

    private void addDataToMetric(String metricName, float metricValue, LineChart chart, List<Entry> metricEntries) {

        long timestamp = System.currentTimeMillis();
        if (metricName.equals("weight")){
            DatabaseReference ref = FirebaseDatabase.getInstance(FirebaseUrl).getReference("Registered Users")
                    .child(userId).child(metricName);

            ref.setValue(String.valueOf(metricValue));
        }
        metricEntries.add(new Entry(timestamp, metricValue));
        // Logic to add data to Firebase under the specific metric for the current user
        DatabaseReference ref = FirebaseDatabase.getInstance(FirebaseUrl).getReference("UserRecords")
                .child(userId).child(metricName);

        ref.child(String.valueOf(timestamp)).setValue(String.valueOf(metricValue))
                .addOnSuccessListener(aVoid -> {
                    // Handle success
                    Toast.makeText(MainActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Toast.makeText(MainActivity.this, "Data to update weight", Toast.LENGTH_SHORT).show();
                });
        setupChart(chart, metricName,metricEntries);
    }


    private void fetchDataForMetric(String metricName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(FirebaseUrl);
        DatabaseReference metricDataRef = database.getReference("UserRecords").child(userId).child(metricName);

        metricDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Entry> metricEntries = new ArrayList<>();
                for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                    String date = entrySnapshot.getKey();
                    float value = Float.parseFloat(entrySnapshot.getValue(String.class));
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
                        long timestamp = sdf.parse(date).getTime();
                        metricEntries.add(new Entry(timestamp, value));
                        Log.d("MainActivity", date);
                    } catch (ParseException e) {
                        Log.e("fetchDataForMetric", "Error parsing date for metric " + metricName, e);
                    }
                }
                // Now that we have all the data for this metric, add its card
                addMetricCard(metricName, metricEntries);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(metricName, "loadMetric:onCancelled", databaseError.toException());
            }
        });
    }
//    private void updateWeightInDatabase(float newWeight) {
//        long timestamp = System.currentTimeMillis();
//        DatabaseReference weightRef = FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app").getReference("UserRecords").child(userId).child("weight_records");
//
//        weightRef.child(String.valueOf(timestamp)).setValue(String.valueOf(newWeight))
//                .addOnSuccessListener(aVoid -> {
//                    // Handle success
//                    Toast.makeText(MainActivity.this, "Weight updated successfully", Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(e -> {
//                    // Handle failure
//                    Toast.makeText(MainActivity.this, "Failed to update weight", Toast.LENGTH_SHORT).show();
//                });
//
//        FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app")
//                .getReference("Registered Users").child(userId).child("weight")
//                .setValue(String.valueOf(newWeight)).addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        // Update was successful
//                        Log.d("UpdateWeight", "Weight updated successfully.");
//                    } else {
//                        // Update failed
//                        Log.d("UpdateWeight", "Failed to update weight.", task.getException());
//                    }
//                });
//    }


//    private void updateWeightInDatabase(float newWeight) {
//        long timestamp = System.currentTimeMillis();
//        DatabaseReference weightRef = FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app").getReference("UserRecords").child(userId).child("weight_records");
//
//        weightRef.child(String.valueOf(timestamp)).setValue(String.valueOf(newWeight))
//                .addOnSuccessListener(aVoid -> {
//                    // Handle success
//                    Toast.makeText(MainActivity.this, "Weight updated successfully", Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(e -> {
//                    // Handle failure
//                    Toast.makeText(MainActivity.this, "Failed to update weight", Toast.LENGTH_SHORT).show();
//                });
//
//        FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app")
//                .getReference("Registered Users").child(userId).child("weight")
//                .setValue(String.valueOf(newWeight)).addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        // Update was successful
//                        Log.d("UpdateWeight", "Weight updated successfully.");
//                    } else {
//                        // Update failed
//                        Log.d("UpdateWeight", "Failed to update weight.", task.getException());
//                    }
//                });
//    }

//    private void fetchdata(){
//
//        authProfile = FirebaseAuth.getInstance();
//        FirebaseUser fbUser = authProfile.getCurrentUser();
//        FirebaseDatabase.getInstance("https://esp-g13-trainify-default-rtdb.europe-west1.firebasedatabase.app").getReference("UserRecords").child(fbUser.getUid()).child("weight_records").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                weightEntries.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                    String date = snapshot.getKey();
//                    float weight = Float.parseFloat(snapshot.getValue(String.class));
//                    try {
//                        // Convert date string to timestamp
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
//                        long timestamp = sdf.parse(date).getTime();
//                        weightEntries.add(new Entry(timestamp, weight));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//                updateChart();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle possible errors
//            }
//        });
//    }


//    private void updateChart() {
//        Description description = new Description();
//        description.setText("Weight Record");
//        description.setPosition(150f, 15f);
//        chart.setDescription(description);
//        chart.getAxisRight().setDrawLabels(false);
//
//        List<Entry> indexedEntries = new ArrayList<>();
//        final List<String> dateLabels = new ArrayList<>();
//        float totalWeight = 0;
//        String timestampAsString = "1710441014884";
//
//        // Assuming weightEntries is already populated with your data
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
//        SimpleDateFormat labelFormat = new SimpleDateFormat("dd/MM/yy", Locale.US);
//
//        for (int i = 0; i < weightEntries.size(); i++) {
//            float weight = weightEntries.get(i).getY();
//            totalWeight += weight;
//
//            long timestamp = (long) weightEntries.get(i).getX();
//            Date date = new Date(timestamp);
//
//            // Use index as X value for equal spacing on the chart
//            indexedEntries.add(new Entry(i, weight));
//
//            // Assuming the timestamp represents a date, convert it back to the readable format
//            String dateString = labelFormat.format(date);
//            dateLabels.add(dateString);
//        }
//
//
//        // Calculate mean weight
//        float meanWeight = totalWeight / weightEntries.size();
//
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawAxisLine(true);
//        xAxis.setDrawGridLines(false);
//        xAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                // Ensure the index is within the bounds of the dateLabels list
//                int index = (int)value;
//                if(index >= 0 && index < dateLabels.size()) {
//                    return dateLabels.get(index);
//                }
//                return ""; // Return an empty string to avoid index out of bounds error
//            }
//        });
//
//        xAxis.setGranularity(1f); // Ensure each index has a label
//        xAxis.setAxisLineColor(Color.BLACK);
//        xAxis.setAxisLineWidth(2f);
//        xAxis.setLabelCount(Math.min(dateLabels.size(), 5)); // Limit the number of labels to the smaller of 5 or the size of dateLabels
//
//
//        YAxis yAxis = chart.getAxisLeft();
//        // Set Y-axis bounds based on mean weight ± 10kg
//        yAxis.setAxisMinimum(meanWeight - 10);
//        yAxis.setAxisMaximum(meanWeight + 10);
//        yAxis.setAxisLineWidth(2f);
//        yAxis.setAxisLineColor(Color.BLACK);
//        yAxis.setLabelCount(4);
//
//        LineDataSet dataSet = new LineDataSet(indexedEntries, "Weight");
//        dataSet.setColor(Color.BLUE);
//        LineData lineData = new LineData(dataSet);
//        chart.setData(lineData);
//        chart.invalidate();
//    }



//    private void addDataToMetric(String metricDescription, float data) {
//        long timestamp = System.currentTimeMillis();
//        // Logic to add data to Firebase under the specific metric for the current user
//        DatabaseReference ref = FirebaseDatabase.getInstance(FirebaseUrl).getReference("UserRecords")
//                .child(userId).child(metricDescription);
//
//        String key = String.valueOf(timestamp);
//        if (key != null) {
//            ref.child(key).setValue(data)
//                    .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show())
//                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed to add data", Toast.LENGTH_SHORT).show());
//        }
//    }
//
//
//
}//        EditText etNewWeight = findViewById(R.id.etNewWeight);
//        Button btnChangeWeight = findViewById(R.id.btnChangeWeight);

//        btnChangeWeight.setOnClickListener(view -> {
//            String newWeightStr = etNewWeight.getText().toString();
//            if(!newWeightStr.isEmpty()) {
//                float newWeight = Float.parseFloat(newWeightStr);
//                updateWeightInDatabase(newWeight);
//            } else {
//                etNewWeight.setError("Please enter your weight");
//            }
//        });
//        fetchdata();