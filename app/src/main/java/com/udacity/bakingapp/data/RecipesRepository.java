package com.udacity.bakingapp.data;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.udacity.bakingapp.api.GetDataService;
import com.udacity.bakingapp.api.RetrofitClientInstance;
import com.udacity.bakingapp.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesRepository {

    private volatile static RecipesRepository INSTANCE = null;
    private GetDataService service;
    private final MutableLiveData<ArrayList<Recipe>> data = new MutableLiveData<>();


    // Prevent direct instantiation.
    private RecipesRepository() {
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @return the {@link RecipesRepository} instance
     */
    public static RecipesRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (RecipesRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RecipesRepository();
                }
            }
        }
        return INSTANCE;
    }

    public MutableLiveData<ArrayList<Recipe>> getAllRecipes() {
        Call<ArrayList<Recipe>> call = service.getAllRecipes();
         call.enqueue(new Callback<ArrayList<Recipe>>() {
             @Override
             public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                 if (response.code() == 200) {
//                     Log.e("200", response.body().toString());
                     data.postValue(response.body());
                 }
             }

             @Override
             public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                 Log.e("fail", t.getMessage());
                 data.postValue(null);
             }
         });
         return data;
    }


}
