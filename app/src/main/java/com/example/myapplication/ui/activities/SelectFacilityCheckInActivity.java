package com.example.myapplication.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.ui.adapters.FacilityRecyclerViewAdapterCheckIn;

import java.util.List;

public class SelectFacilityCheckInActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Facility> facilityList;

    public SelectFacilityCheckInActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
    }


    private List<Facility> getFacilityList(){
        return (List<Facility>) getIntent().getSerializableExtra("FacilityByDistance2");
    }

    private void initView(){
        setContentView(R.layout.activity_select_facility2);
        facilityList= getFacilityList();
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    /**
     * Initialize adapter for recyclerview.
     *
     * @author Ruan Donglin
     */
    private void initAdapter(){
        mAdapter= new FacilityRecyclerViewAdapterCheckIn(SelectFacilityCheckInActivity.this, facilityList);
        LinearLayoutManager manager = new LinearLayoutManager(SelectFacilityCheckInActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }
}