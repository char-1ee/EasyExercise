package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutPlan;
import com.example.myapplication.databases.WorkoutPlanQueryImp;
import com.example.myapplication.ui.adapters.AddPlanAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddPlanActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,OnMapReadyCallback  {
    private Button button;
    private RecyclerView rv_test;
    private Facility facility;
    private GoogleMap mMap;
    private AddPlanAdapter firstAdapter;
    private TextView facilityView;
    private TextView addressView;
    private TextView postalView;
    private Sport finalSport;
    private WorkoutPlan workoutPlan;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initMap();
        initAdapter();
        initButton();
    }

    private Facility getFacility(){
        return (Facility) getIntent().getSerializableExtra("ChosenFacility");
    }

    /**
     * Set google map and add marker for the designated location
     *
     * @param googleMap the main class of the Google Maps SDK for Android
     * @author Ruan Donglin
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Coordinates c= facility.getCoordinates();
        LatLng cur = new LatLng(c.getLatitude(), c.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(cur)
                .title(facility.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15f));
    }

    private void initView(){
        setContentView(R.layout.activity_add_plan);
        facility= getFacility();
        rv_test = findViewById(R.id.check_in_sport_recycler);
        button = findViewById(R.id.add_plan_button);
        facilityView= findViewById(R.id.location_view);
        addressView= findViewById(R.id.address_view);
        postalView= findViewById(R.id.postal_view);
        facilityView.setText(facility.getName());
        addressView.setText(facility.getAddress());
        postalView.setText(facility.getPostalCode());
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Initialize map fragment for displaying google map.
     *
     * @author Ruan Donglin
     */
    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void initAdapter(){
        rv_test.setLayoutManager(new LinearLayoutManager(AddPlanActivity.this, LinearLayoutManager.VERTICAL, false));
        firstAdapter = new AddPlanAdapter(AddPlanActivity.this, facility);
        rv_test.setAdapter(firstAdapter);
        firstAdapter.setOnItemClickListener(this);
    }

    private void initButton(){
        button.setOnClickListener(view -> {
            if(finalSport== null){
                Toast.makeText(AddPlanActivity.this, "Please Select A Sport", Toast.LENGTH_SHORT).show();
            }
            else{
                workoutPlan= new WorkoutPlan(finalSport, facility, 0, WorkoutPlan.WorkoutPlanStatus.PRIVATE);
                //WorkoutPlanQueryImp workoutPlanQueryImp = new WorkoutPlanQueryImp();
                //workoutPlanQueryImp.insert(workoutPlan);
                Intent intent= new Intent(AddPlanActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        finalSport= firstAdapter.finalChoice;
        Toast.makeText(AddPlanActivity.this,String.valueOf(finalSport.getName()), Toast.LENGTH_SHORT).show();
    }

    // TODO: 2021/10/24 add plan

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}