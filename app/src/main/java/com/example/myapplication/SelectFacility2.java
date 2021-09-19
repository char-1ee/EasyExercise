package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class SelectFacility2 extends AppCompatActivity {

    private List<Facility> mFacilityList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_facility2);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new FacilityRecyclerViewAdapter2(SelectFacility2.this, getListData());
        LinearLayoutManager manager = new LinearLayoutManager(SelectFacility2.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        Intent intent0;
                        intent0 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.plan:
                        Intent intent1;
                        intent1 = new Intent(getApplicationContext(), SelectSport.class);
                        startActivity(intent1);
                        break;
                    case R.id.history:
                        Intent intent2;
                        intent2 = new Intent(getApplicationContext(), ViewHistory.class);
                        startActivity(intent2);
                        break;
                    case R.id.me:
                        Intent intent3;
                        intent3 = new Intent(getApplicationContext(), ViewHistory.class);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });


    }

    private List<Facility> getListData(){
        mFacilityList = new ArrayList<Facility>();
        List<Sport> mSportList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            mSportList.add(new Sport("Swimming",R.drawable.swimming, true));
        }
        for (int i = 1; i <= 25; i++) {
            mFacilityList.add(new Facility("north hill", "84073568", "64 Nanyang Cres, Singapore 636959", R.drawable.tanjong, mSportList));
        }
        return mFacilityList;
    }
}