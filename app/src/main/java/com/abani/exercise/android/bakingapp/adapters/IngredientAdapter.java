package com.abani.exercise.android.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abani.exercise.android.bakingapp.R;
import com.abani.exercise.android.bakingapp.models.BakingItemResponse;
import com.abani.exercise.android.bakingapp.models.Ingredient;
import com.abani.exercise.android.bakingapp.utils.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredient> ingredients;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ingredient)
        public TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public IngredientAdapter(List<Ingredient> responses) {
        ingredients = responses;
    }

    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_list_item, parent, false);

        IngredientAdapter.ViewHolder vh = new IngredientAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.ViewHolder holder, final int position) {

        holder.mTextView.setText(CommonUtil.formatIngrendientText(ingredients.get(position), position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setingredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
