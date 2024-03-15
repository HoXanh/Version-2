package com.fitfusion.myapplication.Model;

public class MetricDataPoint {
    private float value;
    private long timestamp; // Consider storing timestamps to order data points

    public MetricDataPoint() {
        // Default constructor for Firebase
    }

    public MetricDataPoint(float value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public float getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
