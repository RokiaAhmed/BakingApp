package com.udacity.bakingapp.ui.recipelist;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.udacity.bakingapp.data.RecipesRepository;
import com.udacity.bakingapp.model.Recipe;

import java.util.ArrayList;

public class RecipeListViewModel extends AndroidViewModel {
    private RecipesRepository repository;

    public RecipeListViewModel(@NonNull Application application) {
        super(application);
        //repository instance
        repository = RecipesRepository.getInstance();
    }

    public MutableLiveData<ArrayList<Recipe>> getAllRecipes(){
        MutableLiveData<ArrayList<Recipe>> s = repository.getAllRecipes();
        return s;
    }
}
