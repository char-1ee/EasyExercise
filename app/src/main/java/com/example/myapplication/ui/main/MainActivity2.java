package com.example.myapplication.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.ui.checkin.CheckIn;
import com.example.myapplication.R;
import com.example.myapplication.ui.AddPlan.SelectSport;
import com.example.myapplication.ui.ViewPlan.ViewPlan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {
    private Button mMakePlanButton, mCheckInButton;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mMakePlanButton = (Button) findViewById(R.id.make_plan_button);
        mCheckInButton = (Button) findViewById(R.id.check_in_button);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigation_plans:
                        Intent intent1;
                        intent1 = new Intent(MainActivity2.this, ViewPlan.class);
                        startActivity(intent1);
                        return true;
                    case R.id.workOutHistory:
                        Intent intent2;
                        intent2 = new Intent(MainActivity2.this, ViewPlan.class);
                        startActivity(intent2);
                        return true;
                    case R.id.navigation_me:
                        Intent intent3;
                        intent3 = new Intent(MainActivity2.this, ViewPlan.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });

        mMakePlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = MainActivity2.this;
                Class destinationActivity = SelectSport.class;
                Intent startChildActivityIntent = new Intent(context, destinationActivity);
                startActivity(startChildActivityIntent);
            }
        });

        mCheckInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = MainActivity2.this;
                Class destinationActivity = CheckIn.class;
                Intent startChildActivityIntent = new Intent(context, destinationActivity);
                startActivity(startChildActivityIntent);
            }
        });
    }
}