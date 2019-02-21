package com.udacity.bakingapp.ui.recipeDetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.ui.recipelist.RecipeListActivity;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity implements StepActionListener{

    private Recipe recipeDetails;
    private ImageView expandImageView, expandLessImageView;
    private TextView ingredientListTextView;
    private RecyclerView stepsRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if (getIntent().getExtras().containsKey(RecipeListActivity.RECIPE_OBJECT)) {
            recipeDetails = (Recipe) getIntent().getExtras().getSerializable(RecipeListActivity.RECIPE_OBJECT);
        }
        setTitle(recipeDetails.getName());
        expandImageView = findViewById(R.id.iv_expand);
        expandLessImageView = findViewById(R.id.iv_expand_less);
        ingredientListTextView = findViewById(R.id.tv_ingredient_list);
        stepsRecyclerView = findViewById(R.id.rv_steps_list);
        linearLayoutManager = new LinearLayoutManager(this);
        stepsRecyclerView.setLayoutManager(linearLayoutManager);
        stepsRecyclerView.setHasFixedSize(true);
        RecipeStepsAdapter stepsAdapter = new RecipeStepsAdapter(this, recipeDetails.getSteps());
        stepsRecyclerView.setAdapter(stepsAdapter);

        prepareIngredient(recipeDetails.getIngredients());

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

    public void expandIngredient(View view) {
        expandImageView.setVisibility(View.GONE);
        expandLessImageView.setVisibility(View.VISIBLE);
        ingredientListTextView.setVisibility(View.VISIBLE);
    }

    public void expandLessIngredient(View view) {
        expandImageView.setVisibility(View.VISIBLE);
        expandLessImageView.setVisibility(View.GONE);
        ingredientListTextView.setVisibility(View.GONE);
    }

    @Override
    public void onStepClicked(Recipe recipe) {

    }
}
