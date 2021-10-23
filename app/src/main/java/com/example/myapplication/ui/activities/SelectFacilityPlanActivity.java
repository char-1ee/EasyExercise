package com.example.myapplication.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.ui.adapters.FacilityRecyclerViewAdapterPlan;

import java.util.List;

public class SelectFacilityPlanActivity extends AppCompatActivity {
    private List<Facility> FacilityQualified;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Coordinates coordinate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
    }

    private Coordinates getCoordinate(){
        return (Coordinates) getIntent().getSerializableExtra("Coordinate");
    }

    private List<Facility> getFacilityQualified(){
        return (List<Facility>) getIntent().getSerializableExtra("FacilityQualified");
    }

    private void initView(){
        setContentView(R.layout.activity_select_facility);
        coordinate= getCoordinate();
        FacilityQualified= getFacilityQualified();
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new FacilityRecyclerViewAdapterPlan(SelectFacilityPlanActivity.this, FacilityQualified, coordinate);
    }

    /**
     * Initialize adapter for recyclerview.
     *
     * @author Ruan Donglin
     */
    private void initAdapter(){
        LinearLayoutManager manager = new LinearLayoutManager(SelectFacilityPlanActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

}