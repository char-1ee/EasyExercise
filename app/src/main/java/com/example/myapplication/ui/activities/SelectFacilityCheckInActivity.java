package com.example.myapplication.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.SportType;
import com.example.myapplication.ui.adapters.FacilityRecyclerViewAdapterCheckIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectFacilityCheckInActivity extends AppCompatActivity {
    private List<Facility> mFacilityList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_facility2);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new FacilityRecyclerViewAdapterCheckIn(SelectFacilityCheckInActivity.this, getListData());
        LinearLayoutManager manager = new LinearLayoutManager(SelectFacilityCheckInActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<Facility> getListData() {
        mFacilityList = new ArrayList<Facility>();
        List<Sport> mSportList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
//            mSportList.add(new SportTable("Swimming", R.drawable.swimming, SportType.INDOOR_OUTDOOR));
        }
        for (int i = 1; i <= 5; i++) {
//            mFacilityList.add(new FacilityTable(
//                    "North Hill",
//                    "https://www.ntu.edu.sg",
//                    "64 Nanyang Crescent",
//                    "636959",
//                    "NA",
//                    R.drawable.tanjong,
//                    new HashMap<String, String>(),
//                    new ArrayList<String>(),
//                    new ArrayList<String>(),
//                    1.3541,
//                    103.6882));
        }
        return mFacilityList;
    }
}