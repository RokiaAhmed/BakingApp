package com.udacity.bakingapp.ui.stepdetails;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.udacity.bakingapp.R;

import com.udacity.bakingapp.model.Step;

public class StepDetailsActivity extends AppCompatActivity {

    Step stepDetails;
    String videoUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        if (getIntent().getExtras().containsKey("step_details")){
         stepDetails = (Step) getIntent().getExtras().get("step_details");
        }
        videoUrl = stepDetails.getVideoURL();
        Log.d("videoUrl", videoUrl);

        FragmentManager fragmentManager = getSupportFragmentManager();
        // Create a new head BodyPartFragment
        StepFragment videoFragment = new StepFragment();

        // Set the list of image id's for the head fragment and set the position to the second image in the list
        videoFragment.setVideoUrl(videoUrl);
        fragmentManager.beginTransaction()
                .add(R.id.step_container, videoFragment)
                .commit();
    }
}
