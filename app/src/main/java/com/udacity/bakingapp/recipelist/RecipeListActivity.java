package com.udacity.bakingapp.recipelist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.RecipesRepository;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.utills.ConnectionDetector;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {

    private RecipeListViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = obtainViewModel(this);

        ConnectionDetector connectionDetector = new ConnectionDetector(this);
        if (connectionDetector.isConnectingToInternet()) {
            mViewModel.getAllRecipes().observe(this, new Observer<ArrayList<Recipe>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Recipe> recipes) {

                }
            });
        } else {

        }
    }

    public static RecipeListViewModel obtainViewModel(FragmentActivity activity) {
        RecipeListViewModel viewModel =
                ViewModelProviders.of(activity).get(RecipeListViewModel.class);
        return viewModel;
    }
}
