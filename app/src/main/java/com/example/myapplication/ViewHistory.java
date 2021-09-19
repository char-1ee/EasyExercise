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

public class ViewHistory extends AppCompatActivity {

    private List<History> mHistoryList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new HistoryRecyclerViewAdapter(getListData());
        LinearLayoutManager manager = new LinearLayoutManager(ViewHistory.this);
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
                        intent0 = new Intent(ViewHistory.this, MainActivity2.class);
                        startActivity(intent0);
                        return true;

                    case R.id.navigation_plans:
                        Intent intent1;
                        intent1 = new Intent(ViewHistory.this, SelectSport.class);
                        startActivity(intent1);
                        return true;
                    case R.id.navigation_me:
                        Intent intent3;
                        intent3 = new Intent(ViewHistory.this, ViewHistory.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });
    }

    private List<History> getListData() {
        mHistoryList = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            mHistoryList.add(new History("Swimming", R.drawable.swimming, "Northhill", 16.23));
        }
        return mHistoryList;
    }
}