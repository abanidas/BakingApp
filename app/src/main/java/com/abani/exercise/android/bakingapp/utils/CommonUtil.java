package com.abani.exercise.android.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.abani.exercise.android.bakingapp.R;
import com.abani.exercise.android.bakingapp.constants.Constants;
import com.abani.exercise.android.bakingapp.models.BakingItemResponse;
import com.abani.exercise.android.bakingapp.models.Ingredient;
import com.google.gson.Gson;

public class CommonUtil {

    public static int getImageToDisplay(int responseId) {
        if (responseId == 1){
            return R.drawable.nutella_pie;
        } else if (responseId == 2){
            return R.drawable.brownies;
        } else if (responseId == 3){
            return R.drawable.yellow_cake;
        } else if (responseId == 4){
            return R.drawable.cheese_cake;
        } else {
            return R.drawable.ic_baking_app;
        }
    }

    public static String formatIngrendientText(Ingredient ingredient, int position) {
        return (position+1)
                +". "+ingredient.getQuantity()
                + " " + ingredient.getMeasure()
                + " " + ingredient.getIngredient();
    }

    public static BakingItemResponse retrieveWidgetItemFromSharedPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.BAKING_APP_PREFERENCE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constants.LAST_RECIPE_KEY, "");
        return gson.fromJson(json, BakingItemResponse.class);
    }
}
