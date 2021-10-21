package com.example.myapplication.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.fragments.CommunityFragment;
import com.example.myapplication.ui.fragments.HistoryFragment;
import com.example.myapplication.ui.fragments.HomeFragment;
import com.example.myapplication.ui.fragments.MeFragment;
import com.example.myapplication.ui.fragments.PlanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }
    /**
     * Set bottom navigation view with corresponding fragments.
     *
     * @author Ruan Donglin
     */
    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigation_plans:
                        selectedFragment = new PlanFragment();
                        break;
                    case R.id.navigation_community:
                        selectedFragment = new CommunityFragment();
                        break;
                    case R.id.navigation_history:
                        selectedFragment = new HistoryFragment();
                        break;
                    case R.id.navigation_me:
                        selectedFragment = new MeFragment();
                }
                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            };
}