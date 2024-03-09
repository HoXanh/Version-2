package com.fitfusion.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fitfusion.myapplication.Model.FitnessPlan;

import java.util.List;

public class FitnessPlanAdapter extends BaseAdapter {

    private Context context;
    private List<FitnessPlan> fitnessPlans;

    public FitnessPlanAdapter(Context context, List<FitnessPlan> fitnessPlans) {
        this.context = context;
        this.fitnessPlans = fitnessPlans;
    }

    @Override
    public int getCount() {
        return fitnessPlans.size();
    }

    @Override
    public Object getItem(int position) {
        return fitnessPlans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fitness_plan, parent, false);
        }
        FitnessPlan plan = fitnessPlans.get(position);

        TextView title = convertView.findViewById(R.id.textViewTitle);
        TextView level = convertView.findViewById(R.id.textViewLevel);
        TextView duration = convertView.findViewById(R.id.textViewDuration);
        ImageView imageView = convertView.findViewById(R.id.imageViewPlanImage);
        Button button = convertView.findViewById(R.id.buttonGetStarted);

        title.setText(plan.getTitle());
        level.setText(plan.getLevel());
        duration.setText(plan.getDuration());
        Glide.with(context).load(plan.getImage()).into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int planId = plan.getId(); // Assuming your FitnessPlan model has a getId() method.
                Intent intent = new Intent(context, ExerciseActivity.class);
                intent.putExtra("NUMBER_KEY", planId);
                context.startActivity(intent); // Use context to call startActivity
                Toast.makeText(context, "Plan clicked: " + plan.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }

    public void updateData(List<FitnessPlan> newFitnessPlans) {
        notifyDataSetChanged(); // Notify the adapter to refresh the list
    }
}
