package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.adapters.CheckInSportAdapter;

import java.util.ArrayList;

public class CheckInActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button button1, button2;
    private RecyclerView rv_test;
    private ArrayList<Sport> secondList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        secondList.add(new Sport("Swimming", R.drawable.swimming, true));
        secondList.add(new Sport("Swimming", R.drawable.swimming, true));
        secondList.add(new Sport("Swimming", R.drawable.swimming, true));
        rv_test = findViewById(R.id.check_in_sport_recycler_view);
        button1 = findViewById(R.id.check_in_sport_choice_button);
        button2 = findViewById(R.id.choose_another_facility_button);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.swimming);

        // RecyclerView adapter
        rv_test.setLayoutManager(new LinearLayoutManager(CheckInActivity.this, LinearLayoutManager.VERTICAL, false));
        CheckInSportAdapter firstAdapter = new CheckInSportAdapter(CheckInActivity.this, secondList);
        rv_test.setAdapter(firstAdapter);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CheckInActivity.this, "Option " + firstAdapter.finalChoice.getText() + " selected", Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckInActivity.this, SelectFacility2Activity.class);
                startActivity(intent);

            }
        });

//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


    }

//    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment= null;
//                    switch (item.getItemId()){
//                        case R.id.navigation_home:
//                            selectedFragment= new FragmentHome();
//                            break;
//                        case R.id.navigation_plan:
//                            selectedFragment= new FragmentPlan();
//                            break;
//                        case R.id.navigation_community:
//                            selectedFragment= new FragmentCommunity();
//                            break;
//                        case R.id.navigation_me:
//                            Intent intent= new Intent(CheckIn.this, ViewMe.class);
//                            startActivity(intent);
//                            finish();
//                            break;
//                    }
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            selectedFragment).commit();
//                    return true;
//                }
//            };
}
