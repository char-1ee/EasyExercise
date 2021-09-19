package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewPlan extends AppCompatActivity {

    private List<WorkOutHistory> mWorkOutHistoryList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plan);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new PlanRecyclerViewAdapter(getListData());
        LinearLayoutManager manager = new LinearLayoutManager(ViewPlan.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigation_home:
                        Intent intent0;
                        intent0 = new Intent(ViewPlan.this, MainActivity.class);
                        startActivity(intent0);
                        return true;
                    case R.id.navigation_plans:
                        Intent intent1;
                        intent1 = new Intent(ViewPlan.this, SelectSport.class);
                        startActivity(intent1);
                        return true;
                    case R.id.navigation_me:
                        Intent intent3;
                        intent3 = new Intent(ViewPlan.this, ViewPlan.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });
    }

    private List<WorkOutHistory> getListData() {
        mWorkOutHistoryList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Sport sport = new Sport("Swimming", R.drawable.swimming, false);
            List<Sport> mSportList = new ArrayList<>();
            mSportList.add(new Sport("Swimming", R.drawable.swimming, true));
            Facility facility = new Facility("north hill", "84073568", "64 Nanyang Cres, Singapore 636959", R.drawable.tanjong, mSportList);
            Date date = new Date(2021, 9, 19);
            Time time = new Time(10, 35, 4);
            WorkOutHistory workOutHistory = new WorkOutHistory(sport, facility, 24, time, true, date);
            mWorkOutHistoryList.add(workOutHistory);
        }
        return mWorkOutHistoryList;
    }
}