package com.udacity.bakingapp.ui.recipelist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private ArrayList<Recipe> recipesList;
    private RecipeActionListener recipeActionListener;

    public RecipeAdapter(RecipeActionListener recipeActionListener, ArrayList<Recipe> recipes) {
        this.recipeActionListener = recipeActionListener;
        recipesList = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recipe, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.recipeNameTextView.setText(recipesList.get(i).getName());
        viewHolder.recipeNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeActionListener.onRecipeClicked(recipesList.get(i));

            }
        });
    }

    public void setRecipeList(ArrayList<Recipe> recipes){
        recipesList = recipes;
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
            TextView recipeNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.tv_recipe_name);
        }
    }
}
