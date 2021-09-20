package com.example.myapplication.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.example.myapplication.ui.ViewMe.FragmentHistory;
import com.example.myapplication.ui.ViewMe.FragmentMe;
import com.example.myapplication.ui.ViewMe.ViewMe;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FragmentHome()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment= null;
                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment= new FragmentHome();
                            break;
                        case R.id.navigation_plan:
                            selectedFragment= new FragmentPlan();
                            break;
                        case R.id.navigation_community:
                            selectedFragment= new FragmentCommunity();
                            break;
                        case R.id.navigation_history:
                            selectedFragment= new FragmentHistory();
                            break;
                        case R.id.navigation_me:
                            selectedFragment= new FragmentMe();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}