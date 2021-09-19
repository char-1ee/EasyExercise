package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

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

//        findViewById(R.id.add_plan_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Context context = MainActivity.this;
//                Class destinationActivity = SelectSport.class;
//                Intent startChildActivityIntent = new Intent(context, destinationActivity);
//                startActivity(startChildActivityIntent);
//            }
//        });
//
//        findViewById(R.id.check_in_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Context context = MainActivity.this;
//                Class destinationActivity = CheckIn.class;
//                Intent startChildActivityIntent = new Intent(context, destinationActivity);
//                startActivity(startChildActivityIntent);
//            }
//        });

//        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}