package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private Button mMakePlanButton, mCheckInButton;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMakePlanButton= (Button)findViewById(R.id.make_plan_button);
        mCheckInButton= (Button)findViewById(R.id.check_in_button);
        mBottomNavigationView= (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.plan:
                        Intent intent1;
                        intent1= new Intent(MainActivity.this, ViewPlan.class);
                        startActivity(intent1);
                        return true;
                    case R.id.workOutHistory:
                        Intent intent2;
                        intent2 = new Intent(MainActivity.this, ViewPlan.class);
                        startActivity(intent2);
                        return true;
                    case R.id.me:
                        Intent intent3;
                        intent3 = new Intent(MainActivity.this, ViewPlan.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });



        mMakePlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context= MainActivity.this;
                Class destinationActivity = SelectSport.class;
                Intent startChildActivityIntent = new Intent(context, destinationActivity);
                startActivity(startChildActivityIntent);
            }
        });

        mCheckInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context= MainActivity.this;
                Class destinationActivity = CheckIn.class;
                Intent startChildActivityIntent = new Intent(context, destinationActivity);
                startActivity(startChildActivityIntent);
            }
        });
    }
}