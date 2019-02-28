package com.udacity.bakingapp.ui.stepdetails;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.udacity.bakingapp.R;

import com.udacity.bakingapp.model.Step;

import java.util.ArrayList;

public class StepDetailsActivity extends AppCompatActivity {
    private ArrayList<Step> stepsList;
    private int currentPosition;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        if (getIntent().getExtras().containsKey("steps_list")) {
            stepsList = (ArrayList<Step>) getIntent().getExtras().get("steps_list");
            currentPosition = getIntent().getIntExtra("position", -1);
        }

        if(savedInstanceState != null){
            currentPosition = savedInstanceState.getInt("position");
            stepFragment.setStepDetails(stepsList.get(currentPosition));
        }

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            // Create a new head BodyPartFragment
            StepFragment stepFragment = new StepFragment();

            // Set the list of image id's for the head fragment and set the position to the second image in the list
            stepFragment.setStepDetails(stepsList.get(currentPosition));
            fragmentManager.beginTransaction()
                    .add(R.id.step_container, stepFragment)
                    .commit();
        }
    }



    public void onPreviousClicked(View view) {
        if (currentPosition == 0){
            return;
        }
            // Create a new head BodyPartFragment
            StepFragment stepFragment = new StepFragment();

        // Set the list of image id's for the head fragment and set the position to the second image in the list
        stepFragment.setStepDetails(stepsList.get(--currentPosition));
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, stepFragment)
                .commit();
    }

    public void onNextClicked(View view) {
        if (currentPosition == stepsList.size() -1){
            return;
        }
        // Create a new head BodyPartFragment
        StepFragment stepFragment = new StepFragment();

        // Set the list of image id's for the head fragment and set the position to the second image in the list
        stepFragment.setStepDetails(stepsList.get(++currentPosition));
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, stepFragment)
                .commit();
    }
}
