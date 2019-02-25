package com.udacity.bakingapp.ui.recipeDetails;

import com.udacity.bakingapp.R;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;

import java.util.ArrayList;


public class StepsListFragment extends Fragment implements StepActionListener {


    private ImageView expandImageView, expandLessImageView;
    private TextView ingredientListTextView;
    private RecyclerView stepsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private static Recipe recipeDetails;
    OnStepClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    public StepsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_list, container, false);

        expandImageView = rootView.findViewById(R.id.iv_expand);
        expandLessImageView = rootView.findViewById(R.id.iv_expand_less);
        ingredientListTextView = rootView.findViewById(R.id.tv_ingredient_list);
        stepsRecyclerView = rootView.findViewById(R.id.rv_steps_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        stepsRecyclerView.setLayoutManager(linearLayoutManager);
        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setFocusable(false);
        StepsAdapter stepsAdapter = new StepsAdapter(this, recipeDetails.getSteps());
        stepsRecyclerView.setAdapter(stepsAdapter);

        expandImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandIngredient();
            }
        });
        expandLessImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandLessIngredient();
            }
        });
        prepareIngredient(recipeDetails.getIngredients());
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    public void setRecipeSteps(Recipe recipeDetails) {
        this.recipeDetails = recipeDetails;
    }

    private void prepareIngredient(ArrayList<Ingredient> ingredients) {
        StringBuilder ingredientList = new StringBuilder();
        ;
        for (Ingredient ingredient : ingredients) {
            ingredientList.append("-  ")
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getMeasure())
                    .append(" ")
                    .append(ingredient.getIngredient())
                    .append(".\n\n");
        }
        ingredientListTextView.setText(ingredientList);
    }

    public void expandIngredient() {
        expandImageView.setVisibility(View.GONE);
        expandLessImageView.setVisibility(View.VISIBLE);
        ingredientListTextView.setVisibility(View.VISIBLE);
    }

    public void expandLessIngredient() {
        expandImageView.setVisibility(View.VISIBLE);
        expandLessImageView.setVisibility(View.GONE);
        ingredientListTextView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(int position) {
        mCallback.onStepSelected(position);
    }
}
