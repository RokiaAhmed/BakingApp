package com.udacity.bakingapp.ui.recipelist;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.udacity.bakingapp.data.RecipesRepository;
import com.udacity.bakingapp.model.Recipe;

import java.util.ArrayList;

public class RecipeListViewModel extends AndroidViewModel {
    private RecipesRepository repository;
    private Context context;

    public RecipeListViewModel(@NonNull Application application) {
        super(application);
        //repository instance
        repository = RecipesRepository.getInstance();
        context = application.getApplicationContext();
    }

    public MutableLiveData<ArrayList<Recipe>> getAllRecipes(){
        MutableLiveData<ArrayList<Recipe>> recipesList = repository.getAllRecipes();
        return recipesList;
    }
}
