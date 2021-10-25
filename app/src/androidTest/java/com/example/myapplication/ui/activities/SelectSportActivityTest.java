package com.example.myapplication.ui.activities;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.R;
import com.example.myapplication.ui.activities.SelectSportActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SelectSportActivityTest {
    @Rule public ActivityTestRule<SelectSportActivity> mActivityTestRule
            = new ActivityTestRule<>(SelectSportActivity.class);

    @Test public void inView_Select_Sport(){
        onView(ViewMatchers.withId(R.id.select_sport)).check(matches(isDisplayed()));
    }

    @Test public void testButton_Confirm_Sport_Choices(){
        onView(withId(R.id.sport_choices_confirm_button)).check(matches(isDisplayed()));
    }

    @Test public void testPress_toSelect_Facility_Plan(){
        onView(withId(R.id.sport_choices_confirm_button)).perform(click());
        onView(withId(R.id.select_facility_plan)).check(matches(isDisplayed()));
    }
}
