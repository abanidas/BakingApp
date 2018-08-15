package com.abani.exercise.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.abani.exercise.android.bakingapp.adapters.RecipeAdapter;
import com.abani.exercise.android.bakingapp.constants.Constants;
import com.abani.exercise.android.bakingapp.models.BakingItemResponse;
import com.abani.exercise.android.bakingapp.networking.ApiClient;
import com.abani.exercise.android.bakingapp.networking.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeItemClickListener{

    private static final String TITLE_BAKING = "Baking time";
    public static final String RECIPE_KEY = "recipe";
    @BindView(R.id.rv_recipe_cards)
    RecyclerView mRvRecipeCards;
    @BindView(R.id.image_logo)
    ImageView imageLogo;

    private RecipeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<BakingItemResponse> responses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(TITLE_BAKING);

        mRvRecipeCards.setHasFixedSize(true);
        if (!getResources().getBoolean(R.bool.isTablet)) {
            mLayoutManager = new LinearLayoutManager(this);
        } else {
            mLayoutManager = new GridLayoutManager(this, 3);
        }
        mRvRecipeCards.setLayoutManager(mLayoutManager);

        responses = new ArrayList<>();
        mAdapter = new RecipeAdapter(responses,this);
        mRvRecipeCards.setAdapter(mAdapter);

        ApiInterface apiInterface = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<BakingItemResponse>> responseCall = apiInterface.getRecipes();
        responseCall.enqueue(new Callback<List<BakingItemResponse>>() {
            @Override
            public void onResponse(Call<List<BakingItemResponse>> call, Response<List<BakingItemResponse>> response) {
                responses = response.body();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setItemResponses(responses);
                        mAdapter.notifyDataSetChanged();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            imageLogo.setTransitionName("");
                        }
                    }
                }, 1000);

            }

            @Override
            public void onFailure(Call<List<BakingItemResponse>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RECIPE_KEY, responses.get(position));
        startActivity(intent);

        SharedPreferences appSharedPrefs = getSharedPreferences(Constants.BAKING_APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(responses.get(position));
        prefsEditor.putString(Constants.LAST_RECIPE_KEY, json);
        prefsEditor.commit();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientAppWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view_widget);
        IngredientAppWidgetProvider.updateIngredientWidgets(this, appWidgetManager,appWidgetIds);
    }
    
}
