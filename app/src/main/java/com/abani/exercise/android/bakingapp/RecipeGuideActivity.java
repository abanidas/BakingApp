package com.abani.exercise.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.abani.exercise.android.bakingapp.models.Step;

import java.util.List;

public class RecipeGuideActivity extends AppCompatActivity {

    private static final String STEP_GUIDE_TITLE = "Step Guide";
    private static final String SAVED_INSTANCE_POSITION = "position";
    private static final String SAVED_PLAYBACK_POSITION = "playback_position";
    private static final String SAVED_PLAYBACK_WINDOW = "current_window";
    private static final String SAVED_PLAY_WHEN_READY = "play_when_ready";
    private List<Step> allSteps;
    private int position = 0;
    RecipeGuideFragment guideFragment;

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

        guideFragment = new RecipeGuideFragment();
        guideFragment.setAllSteps(allSteps);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null){
            position = savedInstanceState.getInt(SAVED_INSTANCE_POSITION);
            guideFragment.setCurrentStep(position);
            guideFragment.setPlaybackPosition(savedInstanceState.getLong(SAVED_PLAYBACK_POSITION));
            guideFragment.setCurrentWindow(savedInstanceState.getInt(SAVED_PLAYBACK_WINDOW));
            guideFragment.setPlayWhenReady(savedInstanceState.getBoolean(SAVED_PLAY_WHEN_READY));
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_guide_container, guideFragment)
                    .commit();
        } else {
            guideFragment.setCurrentStep(position);
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_guide_container, guideFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_POSITION, guideFragment.getCurrentStepPosition());
        outState.putLong(SAVED_PLAYBACK_POSITION, guideFragment.getPlaybackPosition());
        outState.putInt(SAVED_PLAYBACK_WINDOW, guideFragment.getCurrentWindow());
        outState.putBoolean(SAVED_PLAY_WHEN_READY, guideFragment.isPlayWhenReady());
    }
}
