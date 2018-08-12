package com.abani.exercise.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.abani.exercise.android.bakingapp.constants.Constants;
import com.abani.exercise.android.bakingapp.models.BakingItemResponse;
import com.abani.exercise.android.bakingapp.models.Ingredient;
import com.google.gson.Gson;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientAppWidgetProvider extends AppWidgetProvider {

    private static final String INGEREDIENT_TEXT = " Ingredients";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.BAKING_APP_PREFERENCE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constants.LAST_RECIPE_KEY, "");
        BakingItemResponse bakingItem = gson.fromJson(json, BakingItemResponse.class);

        CharSequence widgetText = bakingItem.getName() + INGEREDIENT_TEXT;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        String ingredients = formatIngredients(bakingItem.getIngredients());

        views.setTextViewText(R.id.appwidget_ingredients, ingredients);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static String formatIngredients(List<Ingredient> ingredients) {

        String allIngredients = "";
        for (Ingredient ingredient: ingredients){
            allIngredients = allIngredients + ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient() + "\n\n";
        }
        return allIngredients;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        /*for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/
        updateIngredientWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


}

