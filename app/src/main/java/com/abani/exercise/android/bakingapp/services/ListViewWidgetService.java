package com.abani.exercise.android.bakingapp.services;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.abani.exercise.android.bakingapp.R;
import com.abani.exercise.android.bakingapp.models.BakingItemResponse;
import com.abani.exercise.android.bakingapp.models.Ingredient;
import com.abani.exercise.android.bakingapp.utils.CommonUtil;

public class ListViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private BakingItemResponse bakingItemResponse;

    public ListRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        bakingItemResponse = CommonUtil.retrieveWidgetItemFromSharedPreference(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (bakingItemResponse == null) return 0;
        return bakingItemResponse.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (bakingItemResponse == null) return null;

        Ingredient ingredient = bakingItemResponse.getIngredients().get(position);

        String ingredientContent = CommonUtil.formatIngrendientText(ingredient, position);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget_list_item);

        views.setTextViewText(R.id.text_single_ingredient, ingredientContent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
