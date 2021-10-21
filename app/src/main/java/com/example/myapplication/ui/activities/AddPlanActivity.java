package com.example.myapplication.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.ui.adapters.AddPlanAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddPlanActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button button;
    private RecyclerView rv_test;
    private Facility facility;
    private GoogleMap mMap;
    private AddPlanAdapter firstAdapter;
    private TextView facilityView;
    private TextView addressView;
    private TextView postalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initMap();
        initAdapter();
        initButton();
    }

    private Facility getFacility(){
        Facility f= (Facility) getIntent().getSerializableExtra("ChosenFacility");
        return f;
    }

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
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);
    }

    private void initAdapter(){
        rv_test.setLayoutManager(new LinearLayoutManager(AddPlanActivity.this, LinearLayoutManager.VERTICAL, false));
        firstAdapter = new AddPlanAdapter(AddPlanActivity.this, facility);
        rv_test.setAdapter(firstAdapter);
    }

    private void initButton(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AddPlanActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}