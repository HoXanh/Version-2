package com.fitfusion.myapplication;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.fitfusion.myapplication.Model.Day;
import com.fitfusion.myapplication.Model.FitnessPlan;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;

public class FitnessPlanFilterTest {

    private List<FitnessPlan> allFitnessPlans;

    @Before
    public void setUp() {
        allFitnessPlans = Arrays.asList(
                new FitnessPlan(null, "Yoga Basics", "Beginner", "30 mins", "image_url", "A beginner yoga series"),
                new FitnessPlan(null, "HIIT Burner", "Advanced", "15 mins", "image_url", "High Intensity Interval Training"),
                new FitnessPlan(null, "Meditation Intro", "Beginner", "45 mins", "image_url", "Introduction to meditation")
        );
    }

    @Test
    public void testFilterByLevel() {
        List<FitnessPlan> filtered = FitnessPlanFilter.filter(allFitnessPlans, "Beginner");
        assertEquals(2, filtered.size()); // Expect 2 plans that are for beginners.
    }

    @Test
    public void testFilterByTitle() {
        List<FitnessPlan> filtered = FitnessPlanFilter.filter(allFitnessPlans, "HIIT Burner");
        assertEquals(1, filtered.size()); // Expect 1 plan with the title "HIIT Burner".
        assertEquals("HIIT Burner", filtered.get(0).getTitle());
    }

    @Test
    public void testFilterByTitleandLevel() {
        List<FitnessPlan> filtered = FitnessPlanFilter.filter(allFitnessPlans, "HIIT Burner Advanced");
        assertEquals(1, filtered.size()); // Expect 1 plan with the title "HIIT Burner".
        assertEquals("HIIT Burner", filtered.get(0).getTitle());
    }
    @Test
    public void testCapitalization() {
        List<FitnessPlan> filtered = FitnessPlanFilter.filter(allFitnessPlans, "hiit burner");
        assertEquals(1, filtered.size()); // Expect 1 plan with the title "HIIT Burner".
        assertEquals("HIIT Burner", filtered.get(0).getTitle());
    }
}
