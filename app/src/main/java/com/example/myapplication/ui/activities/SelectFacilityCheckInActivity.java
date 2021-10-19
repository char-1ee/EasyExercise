package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.SportType;
import com.example.myapplication.ui.adapters.FacilityRecyclerViewAdapterCheckIn;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.R;
import com.example.myapplication.beans.Sport;

import java.util.ArrayList;
import java.util.List;

public class SelectFacilityCheckInActivity extends AppCompatActivity {
    private List<Facility> mFacilityList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Facility> facilityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_facility2);
        facilityList= getFacilityList();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter= new FacilityRecyclerViewAdapterCheckIn(SelectFacilityCheckInActivity.this, facilityList);
        LinearLayoutManager manager = new LinearLayoutManager(SelectFacilityCheckInActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }


    private List<Facility> getFacilityList(){
        List<Facility> f= (List<Facility>) getIntent().getSerializableExtra("FacilityByDistance2");
        return f;
    }
}