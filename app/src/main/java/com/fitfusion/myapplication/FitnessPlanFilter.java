package com.fitfusion.myapplication;

import com.fitfusion.myapplication.Model.FitnessPlan;

import java.util.ArrayList;
import java.util.List;

public class FitnessPlanFilter {
    public static List<FitnessPlan> filter(List<FitnessPlan> allFitnessPlans, String query) {
        List<FitnessPlan> filteredList = new ArrayList<>();
        for (FitnessPlan plan : allFitnessPlans) {
            String planLvl = plan.getLevel().toLowerCase();
            String planTitle = plan.getTitle().toLowerCase();
            if (planLvl.contains(query.toLowerCase().trim())
                    || planTitle.contains(query.toLowerCase().trim())
                    || (planTitle + " " + planLvl).equals(query.toLowerCase().trim())
                    || (planLvl + " " + planTitle).equals(query.toLowerCase().trim())) {
                filteredList.add(plan);
            }
        }
        return filteredList;
    }
}
