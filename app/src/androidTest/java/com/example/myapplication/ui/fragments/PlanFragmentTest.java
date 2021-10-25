package com.example.myapplication.ui.fragments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.R;
import com.example.myapplication.ui.activities.MainActivity;
import com.example.myapplication.ui.activities.SelectSportActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

public class PlanFragmentTest {
    @Rule public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test public void inView_Main_Activity(){
        onView(withId(R.id.main_activity)).check(matches(isDisplayed()));
    }

    @Test public void testPlan_Fragment_Navigation(){
        onView(withId(R.id.fragment_container)).perform(click());
        onView(withId(R.id.navigation_plans)).check(matches(isDisplayed()));
    }
}