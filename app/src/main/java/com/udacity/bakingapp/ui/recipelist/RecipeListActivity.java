package com.udacity.bakingapp.ui.recipelist;


import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.RecipeWidgetProvider;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.ui.recipeDetails.RecipeDetailsActivity;
import com.udacity.bakingapp.utills.ConnectionDetector;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity implements RecipeActionListener {

    private RecyclerView recipeRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private RecipeListViewModel mViewModel;
    public static final String RECIPE_OBJECT = "recipe_object";
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mViewModel = obtainViewModel(this);
        recipeRecyclerView = findViewById(R.id.rv_recipe_list);
        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 3);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recipeRecyclerView.setLayoutManager(linearLayoutManager);
        }else {
            recipeRecyclerView.setLayoutManager(gridLayoutManager);
        }
        recipeRecyclerView.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter(this, new ArrayList<Recipe>());
        recipeRecyclerView.setAdapter(recipeAdapter);

        ConnectionDetector connectionDetector = new ConnectionDetector(this);
        if (connectionDetector.isConnectingToInternet()) {
            mViewModel.getAllRecipes().observe(this, new Observer<ArrayList<Recipe>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Recipe> recipes) {
                    recipeAdapter.setRecipeList(recipes);
                    recipeAdapter.notifyDataSetChanged();
                    SharedPreferences offlineDataPrefs = getSharedPreferences(
                            "OFFLINE_DATA_SHARED", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = offlineDataPrefs.edit();
                    editor.putString("recipes", new Gson().toJson(recipes));
                    editor.apply();

                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(RecipeListActivity.this);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(RecipeListActivity.this, RecipeWidgetProvider.class));
                    //Trigger data update to handle the GridView widgets and force a data refresh
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
                    //Now update all widgets
                    RecipeWidgetProvider.updateRecipeWidgets(RecipeListActivity.this, appWidgetManager,appWidgetIds);
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

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RECIPE_OBJECT, recipe);
        startActivity(intent);
    }
}
