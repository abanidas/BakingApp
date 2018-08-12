package com.abani.exercise.android.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abani.exercise.android.bakingapp.adapters.IngredientAdapter;
import com.abani.exercise.android.bakingapp.adapters.StepAdapter;
import com.abani.exercise.android.bakingapp.models.Ingredient;
import com.abani.exercise.android.bakingapp.models.Step;
import com.abani.exercise.android.bakingapp.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends Fragment implements StepAdapter.StepItemClickListener{

    @BindView(R.id.rv_ingredients)
    RecyclerView mRvRecipeIngredients;
    @BindView(R.id.rv_recipe_steps)
    RecyclerView mRvRecipeSteps;

    private int responseId = 0;

    private IngredientAdapter mIngredientAdapter;
    private RecyclerView.LayoutManager mIngredientLayoutManager;

    private List<Ingredient> ingredients = new ArrayList<>();

    private StepAdapter mStepAdapter;
    private RecyclerView.LayoutManager mStepLayoutManager;

    private List<Step> steps = new ArrayList<>();

    private OnRecipeStepItemClickListener itemClickListener;

    public interface OnRecipeStepItemClickListener{
        void onRecipeStepSelected(int position);
    }

    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients_and_steps, container, false);
        ButterKnife.bind(this, rootView);

        mRvRecipeIngredients.setHasFixedSize(true);
        mIngredientLayoutManager = new LinearLayoutManager(getContext());
        mRvRecipeIngredients.setLayoutManager(mIngredientLayoutManager);
        mIngredientAdapter = new IngredientAdapter(ingredients);
        mRvRecipeIngredients.setAdapter(mIngredientAdapter);

        mRvRecipeSteps.setHasFixedSize(true);
        mStepLayoutManager = new LinearLayoutManager(getContext());
        mRvRecipeSteps.setLayoutManager(mStepLayoutManager);
        mStepAdapter = new StepAdapter(steps, this, getContext());
        mStepAdapter.setImageToDisplay(CommonUtil.getImageToDisplay(responseId));
        mRvRecipeSteps.setAdapter(mStepAdapter);
        return rootView;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void setItemClickListener(OnRecipeStepItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onStepItemClick(int position) {
        itemClickListener.onRecipeStepSelected(position);
    }
}
