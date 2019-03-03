package com.udacity.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.ui.recipelist.RecipeListActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    ArrayList<Ingredient> list;
    static String recipeName;

    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        SharedPreferences offlineDataPrefs = mContext.getSharedPreferences(
                "OFFLINE_DATA_SHARED", Context.MODE_PRIVATE);
        String offlineData = offlineDataPrefs.getString("recipes", null);
        if (offlineData != null) {
            ArrayList<Recipe> recipesList = new ArrayList<>(Arrays.asList(new Gson().fromJson(offlineData, Recipe[].class)));
            list = recipesList.get(0).getIngredients();
            recipeName = recipesList.get(0).getName();
            if (recipeName != null){
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, RecipeWidgetProvider.class));
                  //Now update all widgets
                RecipeWidgetProvider.updateRecipeWidgets(mContext, appWidgetManager,appWidgetIds);
            }
        }
    }

    @Override
    public void onDestroy() {

    }

    public static String getRecipeName(){
        return recipeName;
    }

    @Override
    public int getCount() {
        if (list == null) return 0;
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget);
        // Update the recipe text
        views.setTextViewText(R.id.widget_recipe_text, prepareIngredient(list.get(position)));
        return views;
    }

    private static String prepareIngredient(Ingredient ingredient) {
        StringBuilder ingredientList = new StringBuilder();

        ingredientList.append("-  ")
                .append(ingredient.getQuantity())
                .append(" ")
                .append(ingredient.getMeasure())
                .append(" ")
                .append(ingredient.getIngredient())
                .append(".\n\n");

        return ingredientList.toString();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}