package com.udacity.bakingapp.ui.recipeDetails;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.ui.recipelist.RecipeListActivity;
import com.udacity.bakingapp.ui.stepdetails.StepDetailsActivity;


public class RecipeDetailsActivity extends AppCompatActivity implements StepsListFragment.OnStepClickListener{

    private static Recipe recipeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if (getIntent().getExtras().containsKey(RecipeListActivity.RECIPE_OBJECT)) {
            recipeDetails = (Recipe) getIntent().getExtras().getSerializable(RecipeListActivity.RECIPE_OBJECT);
        }
        setTitle(recipeDetails.getName());
        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Create a new head BodyPartFragment
        StepsListFragment stepsFragment = new StepsListFragment();

        // Set the list of image id's for the head fragment and set the position to the second image in the list
        stepsFragment.setRecipeSteps(recipeDetails);
        fragmentManager.beginTransaction()
                .add(R.id.steps_container, stepsFragment)
                .commit();


    }

    @Override
    public void onStepSelected(int position) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putExtra("step_details", recipeDetails.getSteps().get(position));
        startActivity(intent);
    }
}
