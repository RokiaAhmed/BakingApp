package com.udacity.bakingapp.ui.recipeDetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Step;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private ArrayList<Step> stepsList;
    private StepActionListener recipeActionListener;

    public StepsAdapter(StepActionListener recipeActionListener, ArrayList<Step> steps) {
        this.recipeActionListener = recipeActionListener;
        stepsList = steps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_step, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.stepNumberTextView.setText(String.valueOf(i+1));
        viewHolder.stepShortDescriptionTextView.setText(stepsList.get(i).getShortDescription());
        viewHolder.stepShortDescriptionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeActionListener.onClick(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
            TextView stepShortDescriptionTextView, stepNumberTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stepShortDescriptionTextView = itemView.findViewById(R.id.tv_step_short_description);
            stepNumberTextView = itemView.findViewById(R.id.tv_step_num);
        }
    }
}
