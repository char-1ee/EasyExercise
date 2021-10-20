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
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plan);
        plan = getChosenPlan();
        facilityView = findViewById(R.id.facility_view);
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
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);

        sportView.setText(sport.getName());
    }

    private WorkoutPlan getChosenPlan() {
        WorkoutPlan p = (WorkoutPlan) getIntent().getSerializableExtra("ChosenPlan");
        return p;
    }

    private WorkoutPlan getListData() {
        Sport s = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Location location = testCheckinClosetFacility();
        WorkoutPlan w = new WorkoutPlan(s, location, 0, com.example.myapplication.beans.WorkoutPlanStatus.PRIVATE);
        return w;
    }

    private Facility testCheckinClosetFacility() {
        Sport a = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Facility r = new Facility(0, "wave", "http://www.ringoeater.com/", "84073568", "64 Nanyang Cres", "nonononono", new Coordinates(1.3, 103.6));
        r.addSport(a);
        r.addSport(b);
        r.addSport(c);
        return r;
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
}