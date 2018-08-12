package com.abani.exercise.android.bakingapp.utils;

import com.abani.exercise.android.bakingapp.R;
import com.abani.exercise.android.bakingapp.models.Ingredient;

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
}
