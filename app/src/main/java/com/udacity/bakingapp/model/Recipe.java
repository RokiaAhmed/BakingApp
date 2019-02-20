package com.udacity.bakingapp.model;

import java.util.ArrayList;

public class Recipe {
    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step>steps;
    private int servings;
    private String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
