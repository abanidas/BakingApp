package com.abani.exercise.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.abani.exercise.android.bakingapp.constants.Constants;
import com.abani.exercise.android.bakingapp.models.BakingItemResponse;
import com.abani.exercise.android.bakingapp.models.Ingredient;
import com.abani.exercise.android.bakingapp.services.ListViewWidgetService;
import com.abani.exercise.android.bakingapp.utils.CommonUtil;
import com.google.gson.Gson;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientAppWidgetProvider extends AppWidgetProvider {

    public static final String INGEREDIENT_TEXT = " Ingredients";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        BakingItemResponse bakingItem = CommonUtil.retrieveWidgetItemFromSharedPreference(context);

        CharSequence widgetText = bakingItem.getName() + INGEREDIENT_TEXT;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent intent = new Intent(context, ListViewWidgetService.class);
        views.setRemoteAdapter(R.id.list_view_widget, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

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

