package com.udacity.bakingapp;

import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.bakingapp.ui.recipelist.RecipeListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecyclerViewRecipesList {

    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void scrollToPosition() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .perform((ViewAction) RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null){
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
