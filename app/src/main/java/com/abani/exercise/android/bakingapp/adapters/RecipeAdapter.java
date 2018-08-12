package com.abani.exercise.android.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abani.exercise.android.bakingapp.R;
import com.abani.exercise.android.bakingapp.models.BakingItemResponse;
import com.abani.exercise.android.bakingapp.utils.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

    private List<BakingItemResponse> itemResponses;
    private RecipeItemClickListener itemClickListener;

    public interface RecipeItemClickListener {
        void onItemClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_recipe)
        ImageView imageRecipe;
        @BindView(R.id.tv_recipe)
        public TextView tvRecipe;
        @BindView(R.id.tv_servings)
        public TextView tvServings;
        @BindView(R.id.ll_recipe)
        LinearLayout linearLayoutRecipe;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public RecipeAdapter(List<BakingItemResponse> responses, RecipeItemClickListener listener) {
        itemResponses = responses;
        itemClickListener = listener;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvRecipe.setText(itemResponses.get(position).getName());

        holder.imageRecipe.setImageResource(CommonUtil.getImageToDisplay(itemResponses.get(position).getId()));

        holder.tvServings.setText(String.valueOf(itemResponses.get(position).getServings()));

        holder.linearLayoutRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemResponses.size();
    }

    public void setItemResponses(List<BakingItemResponse> itemResponses) {
        this.itemResponses = itemResponses;
    }
}
