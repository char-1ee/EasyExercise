package com.example.myapplication.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.beans.Coordinates;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.ui.adapters.CheckInSportAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckInNormalActivity extends AppCompatActivity implements OnMapReadyCallback{
    private ImageView imageView;
    private Button button1, button2;
    private RecyclerView rv_test;
    private Facility facility;
    private List<Facility> facilityList;
    private Sport ChosenSport;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        facility= getFacility();
        facilityList= getFacilityList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_normal);
        rv_test = findViewById(R.id.check_in_sport_recycler);
        button1 = findViewById(R.id.check_in_sport_button);
        button2 = findViewById(R.id.choose_another_facility_button);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);


        // RecyclerView adapter
        rv_test.setLayoutManager(new LinearLayoutManager(CheckInNormalActivity.this, LinearLayoutManager.VERTICAL, false));
        CheckInSportAdapter firstAdapter = new CheckInSportAdapter(CheckInNormalActivity.this, new ArrayList<>(facility.getSports()));
        rv_test.setAdapter(firstAdapter);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CheckInNormalActivity.this, "Option " + firstAdapter.finalChoice.getName() + " selected", Toast.LENGTH_SHORT).show();
                ChosenSport = firstAdapter.finalChoice;
                Intent intent= new Intent(CheckInNormalActivity.this, ExerciseActivity.class);
                intent.putExtra("ChosenSport", ChosenSport);
                intent.putExtra("ChosenLocation", facility);
                startActivity(intent);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckInNormalActivity.this, SelectFacilityCheckInActivity.class);
                intent.putExtra("FacilityByDistance2",(Serializable)facilityList);
                startActivity(intent);
            }
        });

    }




    private Facility getFacility(){
        Facility f= (Facility) getIntent().getSerializableExtra("ClosestFacility");
        return f;
    }

    private List<Facility> getFacilityList(){
        List<Facility> f= (List<Facility>) getIntent().getSerializableExtra("FacilityByDistance");
        // TODO: 2021/10/11 match facility with plan in database 
        
        return f;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Coordinates c= facility.getCoordinates();
        // Add a marker in Sydney and move the camera
        LatLng cur = new LatLng(c.getLatitude(), c.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(cur)
                .title(facility.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15f));
    }
}
