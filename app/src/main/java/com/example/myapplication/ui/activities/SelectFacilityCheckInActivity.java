package com.example.myapplication.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.ui.adapters.FacilityRecyclerViewAdapterCheckIn;

import java.util.List;

public class SelectFacilityCheckInActivity extends AppCompatActivity {
    TextView textView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Facility> facilityList;
    double latitude= 0;
    double longitude= 0;
    private ActionBar actionBar;

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
        textView= findViewById(R.id.textView7);
        facilityList= getFacilityList();
        mRecyclerView = findViewById(R.id.recycler_view);
        latitude= getLatitude();
        longitude= getLongitude();
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //textView.setText(String.valueOf(latitude));
    }

    /**
     * Initialize adapter for recyclerview.
     *
     * @author Ruan Donglin
     */
    private void initAdapter(){
        mAdapter= new FacilityRecyclerViewAdapterCheckIn(SelectFacilityCheckInActivity.this, facilityList, new Coordinates(latitude, longitude, ""));
        LinearLayoutManager manager = new LinearLayoutManager(SelectFacilityCheckInActivity.this);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}