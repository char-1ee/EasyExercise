package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.SportType;
import com.example.myapplication.ui.adapters.FacilityRecyclerViewAdapter2;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.R;
import com.example.myapplication.beans.Sport;

import java.util.ArrayList;
import java.util.List;

public class SelectFacility2Activity extends AppCompatActivity {
    private List<Facility> mFacilityList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_facility2);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new FacilityRecyclerViewAdapter2(SelectFacility2Activity.this, getListData());
        LinearLayoutManager manager = new LinearLayoutManager(SelectFacility2Activity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<Facility> getListData() {
        mFacilityList = new ArrayList<Facility>();
        List<Sport> mSportList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            mSportList.add(new Sport("Swimming", R.drawable.swimming, SportType.INDOOR_OUTDOOR));
        }
        for (int i = 1; i <= 5; i++) {
            mFacilityList.add(new Facility(
                    new Coordinates(0, 0),
                    "North Hill",
                    "https://www.ntu.edu.sg",
                    "84073568",
                    "64 Nanyang Cres, Singapore 636959",
                    R.drawable.tanjong,
                    mSportList));
        }
        return mFacilityList;
    }
}