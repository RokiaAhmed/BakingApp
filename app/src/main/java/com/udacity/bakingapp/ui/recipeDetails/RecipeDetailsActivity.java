package com.udacity.bakingapp.ui.recipeDetails;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.ui.recipelist.RecipeListActivity;
import com.udacity.bakingapp.ui.stepdetails.StepDetailsActivity;
import com.udacity.bakingapp.ui.stepdetails.StepFragment;


public class RecipeDetailsActivity extends AppCompatActivity
        implements StepsListFragment.OnStepClickListener {

    private static Recipe recipeDetails;
    private boolean mTwoPane;
    private int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if (getIntent().getExtras().containsKey(RecipeListActivity.RECIPE_OBJECT)) {
            recipeDetails = (Recipe) getIntent().getExtras().getSerializable(RecipeListActivity.RECIPE_OBJECT);
        }
        setTitle(recipeDetails.getName());

        if (findViewById(R.id.step_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                StepFragment stepFragment = new StepFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.step_container, stepFragment)
                        .commit();

            }

        } else {
            mTwoPane = false;
        }
        // Add the fragment to its container using a FragmentManager and a Transaction
        // Create a new head BodyPartFragment

        if (savedInstanceState == null) {
            StepsListFragment stepsFragment = new StepsListFragment();

            // Set the list of image id's for the head fragment and set the position to the second image in the list
            stepsFragment.setRecipeSteps(recipeDetails);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.steps_container, stepsFragment)
                    .commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }

    @Override
    public void onStepSelected(int position) {
        if (mTwoPane) {
            StepFragment stepFragment = new StepFragment();
            currentPosition = position;
            stepFragment.setStepDetails(recipeDetails.getSteps().get(position));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_container, stepFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra("steps_list", recipeDetails.getSteps());
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }
}
