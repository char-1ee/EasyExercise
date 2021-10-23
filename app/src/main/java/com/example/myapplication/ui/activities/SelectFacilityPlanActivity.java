package com.example.myapplication.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.ui.adapters.FacilityRecyclerViewAdapterPlan;

import java.awt.font.TextAttribute;
import java.util.List;

public class SelectFacilityPlanActivity extends AppCompatActivity {
    private TextView textView;
    private List<Facility> FacilityQualified;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Coordinates coordinate;
    double latitude= 0;
    double longitude= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
    }



    private List<Facility> getFacilityQualified(){
        return (List<Facility>) getIntent().getSerializableExtra("FacilityQualified");
    }

    private void initView(){
        setContentView(R.layout.activity_select_facility);
        latitude= getLatitude();
        longitude= getLongitude();
        FacilityQualified= getFacilityQualified();
        mRecyclerView = findViewById(R.id.recycler_view);
        textView= findViewById(R.id.textView6);
        textView.setText(String.valueOf(latitude));

        mAdapter = new FacilityRecyclerViewAdapterPlan(SelectFacilityPlanActivity.this, FacilityQualified, new Coordinates(latitude, longitude, ""));
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

    public double getLatitude() {
        return (double) getIntent().getSerializableExtra("latitude");
    }

    public double getLongitude() {
        return (double) getIntent().getSerializableExtra("longitude");
    }

}