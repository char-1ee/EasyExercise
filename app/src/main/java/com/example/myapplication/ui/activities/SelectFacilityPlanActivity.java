package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.R;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.SportType;
import com.example.myapplication.ui.adapters.FacilityRecyclerViewAdapterPlan;

import java.util.ArrayList;
import java.util.List;

public class SelectFacilityPlanActivity extends AppCompatActivity {
    private List<Facility> FacilityQualified;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
    }

    private List<Facility> getFacilityQualified(){
        List<Facility> f= (List<Facility>) getIntent().getSerializableExtra("FacilityQualified");
        return f;
    }

    private void initView(){
        setContentView(R.layout.activity_select_facility);

        FacilityQualified= getFacilityQualified();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new FacilityRecyclerViewAdapterPlan(SelectFacilityPlanActivity.this, FacilityQualified);
    }

    private void initAdapter(){
        LinearLayoutManager manager = new LinearLayoutManager(SelectFacilityPlanActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

}