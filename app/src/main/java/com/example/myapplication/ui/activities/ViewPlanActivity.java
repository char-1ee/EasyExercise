package com.example.myapplication.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutPlan;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

public class ViewPlanActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Sport sport;
    private TextView postalView;
    private TextView facilityView;
    private TextView sportView;
    private TextView addressView;
    private WorkoutPlan plan;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initMap();
    }

    private WorkoutPlan getChosenPlan() {
        WorkoutPlan p = (WorkoutPlan) getIntent().getSerializableExtra("ChosenPlan");
        return p;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Coordinates c = location.getCoordinates();
        // Add a marker in Sydney and move the camera
        LatLng cur = new LatLng(c.getLatitude(), c.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(cur)
                .title(location.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15f));
    }

    /**
     * Initialize adapter for recyclerview.
     *
     * @author Ruan Donglin
     */
    private void initView(){
        setContentView(R.layout.activity_view_plan);
        plan = getChosenPlan();
        facilityView = findViewById(R.id.location_view);
        sportView = findViewById(R.id.sport_view);
        postalView = findViewById(R.id.postal_view);
        addressView = findViewById(R.id.address_view);
        location = plan.getLocation();
        sport = plan.getSport();
        if (location.getType() == Location.LocationType.FACILITY) {
            Facility f = (Facility) location;
            facilityView.setText(f.getName());
            addressView.setText(f.getAddress());
            postalView.setText(f.getPostalCode());
        } else {
            facilityView.setText("Customized Location");
            addressView.setText("");
            postalView.setText("");
        }
        sportView.setText(sport.getName());
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);
    }
}