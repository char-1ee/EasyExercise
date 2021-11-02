package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.CustomizedLocation;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters.CheckInSportAdapter;

/**
 * The activity class for checking in at a specific facility in the checking in task, when there's facility around.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class CheckInNormalActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemClickListener {
    private double latitude;
    private double longitude;
    private Button buttonSport;
    private Button buttonFacility;
    private Button buttonLocation;
    private RecyclerView recyclerView;
    private Facility facility;
    private List<Facility> facilityList;
    private Sport ChosenSport;
    private CheckInSportAdapter checkInSportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initMap();
        initAdapter();
        initButton();
    }

    private Facility getFacility() {
        return (Facility) getIntent().getSerializableExtra("ClosestFacility");
    }

    private List<Facility> getFacilityList() {
        return (List<Facility>) getIntent().getSerializableExtra("FacilityByDistance");
    }

    /**
     * Set Google Map and add marker for the designated location
     *
     * @param googleMap the main class of the Google Maps SDK for Android
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Coordinates c = facility.getCoordinates();
        LatLng cur = new LatLng(c.getLatitude(), c.getLongitude());
        googleMap.addMarker(new MarkerOptions()
                .position(cur)
                .title(facility.getName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15f));
    }


    private void initView() {
        setContentView(R.layout.activity_check_in_normal);
        facility = getFacility();
        facilityList = getFacilityList();
        latitude = getLatitude();
        longitude = getLongitude();
        buttonLocation= findViewById(R.id.customized_location_button);
        recyclerView = findViewById(R.id.check_in_sport_recycler);
        buttonSport = findViewById(R.id.check_in_sport_button);
        buttonFacility = findViewById(R.id.choose_another_facility_button);
        TextView facilityView = findViewById(R.id.location_view);
        TextView addressView = findViewById(R.id.address_view);
        TextView postalView = findViewById(R.id.postal_view);
        facilityView.setText(facility.getName());
        addressView.setText(facility.getAddress());
        postalView.setText(facility.getPostalCode());
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        CheckInNormalActivity.this,
                        LinearLayoutManager.VERTICAL,
                        false
                ));
        checkInSportAdapter = new CheckInSportAdapter(CheckInNormalActivity.this, new ArrayList<>(facility.getSports()));
        recyclerView.setAdapter(checkInSportAdapter);
        checkInSportAdapter.setOnItemClickListener(this);
    }

    /**
     * Initialize map fragment for displaying Google Map.
     */
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void initButton() {
        buttonSport.setOnClickListener(view -> {
            if (ChosenSport == null) {
                Toast.makeText(
                        CheckInNormalActivity.this,
                        "Please select a sport.",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                Intent intent = new Intent(CheckInNormalActivity.this, ExerciseActivity.class);
                intent.putExtra("ChosenSport", ChosenSport);
                intent.putExtra("ChosenLocation", facility);
                startActivity(intent);
            }

        });

        buttonFacility.setOnClickListener(view -> {
            Intent intent = new Intent(CheckInNormalActivity.this, SelectFacilityCheckInActivity.class);
            intent.putExtra("FacilityByDistance2", (Serializable) facilityList);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        });

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckInNormalActivity.this, CheckInCustomizedActivity.class);
                intent.putExtra("CustomizedLocation", new CustomizedLocation(new Coordinates(latitude, longitude, "Customized Location")));
                intent.putExtra("ClosestFacility", facility);
                intent.putExtra("FacilityByDistance", (Serializable) facilityList);
                startActivity(intent);
                finish();
            }
        });
    }

    public double getLatitude() {
        return (double) getIntent().getSerializableExtra("latitude1");
    }

    public double getLongitude() {
        return (double) getIntent().getSerializableExtra("longitude1");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ChosenSport = checkInSportAdapter.finalChoice;
        Toast.makeText(CheckInNormalActivity.this, String.valueOf(ChosenSport.getName()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
