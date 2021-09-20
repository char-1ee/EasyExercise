package com.example.myapplication.ui.addPlan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.classes.Facility;
import com.example.myapplication.R;
import com.example.myapplication.classes.Sport;

import java.util.ArrayList;
import java.util.List;

public class SelectFacility extends AppCompatActivity {

    private List<Facility> mFacilityList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_facility);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new FacilityRecyclerViewAdapter(SelectFacility.this, getListData());
        LinearLayoutManager manager = new LinearLayoutManager(SelectFacility.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

    }

    private List<Facility> getListData() {
        mFacilityList = new ArrayList<Facility>();
        List<Sport> mSportList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            mSportList.add(new Sport("Swimming", R.drawable.swimming, true));
        }
        for (int i = 1; i <= 25; i++) {
            mFacilityList.add(new Facility("North Hill", "84073568", "64 Nanyang Cres, Singapore 636959", R.drawable.tanjong, mSportList));
        }
        return mFacilityList;
    }
}