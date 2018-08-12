package com.abani.exercise.android.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.abani.exercise.android.bakingapp.adapters.IngredientAdapter;
import com.abani.exercise.android.bakingapp.adapters.StepAdapter;
import com.abani.exercise.android.bakingapp.models.BakingItemResponse;
import com.abani.exercise.android.bakingapp.models.Ingredient;
import com.abani.exercise.android.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements RecipeFragment.OnRecipeStepItemClickListener{


    public static final String PARCELLING_STEPS_KEY = "recipe_guides";
    public static final String CURRENT_CLICKED_STEP = "current_position";
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();
    private BakingItemResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.RECIPE_KEY)){
            response = intent.getParcelableExtra(MainActivity.RECIPE_KEY);
            getSupportActionBar().setTitle(response.getName());
            ingredients = response.getIngredients();
            steps = response.getSteps();
        }

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setResponseId(response.getId());
        recipeFragment.setIngredients(ingredients);
        recipeFragment.setSteps(steps);
        recipeFragment.setItemClickListener(this);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();

        if (getResources().getBoolean(R.bool.isTablet)){
            RecipeGuideFragment guideFragment = new RecipeGuideFragment();
            guideFragment.setAllSteps(steps);
            guideFragment.setCurrentStep(0);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_guide_container, guideFragment)
                    .commit();
        }

    }

    @Override
    public void onRecipeStepSelected(int position) {
        if (!getResources().getBoolean(R.bool.isTablet)) {
            Intent intent = new Intent(this, RecipeGuideActivity.class);
            intent.putParcelableArrayListExtra(PARCELLING_STEPS_KEY, (ArrayList<Step>) steps);
            intent.putExtra(CURRENT_CLICKED_STEP, position);
            startActivity(intent);
        } else {
            RecipeGuideFragment newGuideFragment = new RecipeGuideFragment();
            newGuideFragment.setCurrentStep(position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_guide_container, newGuideFragment)
                    .commit();
        }
    }
}
