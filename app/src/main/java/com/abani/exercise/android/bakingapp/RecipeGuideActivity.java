package com.abani.exercise.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.abani.exercise.android.bakingapp.models.Step;

import java.util.List;

public class RecipeGuideActivity extends AppCompatActivity {

    private static final String STEP_GUIDE_TITLE = "Step Guide";

    private List<Step> allSteps;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_guide);

        Intent passedIntent = getIntent();
        position = 0;
        if (passedIntent != null && passedIntent.hasExtra(RecipeActivity.PARCELLING_STEPS_KEY) && passedIntent.hasExtra(RecipeActivity.CURRENT_CLICKED_STEP)){
            allSteps = passedIntent.getParcelableArrayListExtra(RecipeActivity.PARCELLING_STEPS_KEY);
            position = passedIntent.getIntExtra(RecipeActivity.CURRENT_CLICKED_STEP, 0);
        }

        getSupportActionBar().setTitle(STEP_GUIDE_TITLE);

        if (savedInstanceState == null) {
            RecipeGuideFragment guideFragment = new RecipeGuideFragment();
            guideFragment.setAllSteps(allSteps);
            FragmentManager fragmentManager = getSupportFragmentManager();
            guideFragment.setCurrentStep(position);
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_guide_container, guideFragment)
                    .commit();
        }
    }
}
