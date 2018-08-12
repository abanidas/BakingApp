package com.abani.exercise.android.bakingapp;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityRecyclerViewTest {

    public static final String INGREDIENT_LABEL = "Ingeredients";

    @Rule
    public ActivityTestRule<MainActivity> mAcyivtyTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void click_RecyclerViewItem_opensRecipeActivity(){

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.rv_recipe_cards)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.text_label_ingredient)).check(matches(ViewMatchers.withText(INGREDIENT_LABEL)));
    }
}
