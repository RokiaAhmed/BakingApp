package com.udacity.bakingapp.api;

import com.udacity.bakingapp.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getAllRecipes();

}
