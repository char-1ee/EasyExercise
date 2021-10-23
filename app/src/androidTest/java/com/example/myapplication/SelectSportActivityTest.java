package com.example.myapplication;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.ui.activities.SelectSportActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SelectSportActivityTest {
    @Rule public ActivityTestRule<SelectSportActivity> mActivityTestRule
            = new ActivityTestRule<>(SelectSportActivity.class);
}
