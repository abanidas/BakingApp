package com.abani.exercise.android.bakingapp.adapters;

import android.content.Context;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abani.exercise.android.bakingapp.R;
import com.abani.exercise.android.bakingapp.models.Ingredient;
import com.abani.exercise.android.bakingapp.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private Context mContext;
    private List<Step> steps;
    private StepItemClickListener itemClickListener;
    private int imageToDisplay;

    public void setImageToDisplay(int imageDrawableToDisplay) {
        imageToDisplay = imageDrawableToDisplay;
    }

    public interface StepItemClickListener {
        void onStepItemClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_step)
        ImageView thumbnail;
        @BindView(R.id.tv_step)
        public TextView mTextView;
        @BindView(R.id.ll_recipe_step)
        LinearLayout layoutRecipeStep;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public StepAdapter(List<Step> steps, StepItemClickListener listener, Context context) {
        this.steps = steps;
        itemClickListener = listener;
        mContext = context;
    }

    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list_item, parent, false);

        StepAdapter.ViewHolder vh = new StepAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StepAdapter.ViewHolder holder, final int position) {

        holder.thumbnail.setImageResource(imageToDisplay);
        holder.mTextView.setText(position+1+". "+steps.get(position).getShortDescription());
        holder.layoutRecipeStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onStepItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

}
