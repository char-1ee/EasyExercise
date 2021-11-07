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
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.CustomizedLocation;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.SportAndFacilityDBHelper;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters.CheckInSportAdapter;

/**
 * The activity class for checking in at a customized location in the checking in task, when there's no facility around.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class CheckInCustomizedActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemClickListener {
    private SportAndFacilityDBHelper dbHelper;
    private Button checkInSportButton, selectFacilityButton;
    private RecyclerView recyclerView;
    private CustomizedLocation customizedLocation;
    private Sport ChosenSport;
    private CheckInSportAdapter checkInSportAdapter;
    private Facility facility;
    private List<Facility> facilityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        customizedLocation = getCustomizedLocation();
        facility = getFacility();
        facilityList = getFacilityList();
        super.onCreate(savedInstanceState);
        initView();
        initMap();
        initAdapter();
        initButton();
    }

    /**
     * Set Google Map and add marker for the designated location
     *
     * @param googleMap the main class of the Google Maps SDK for Android
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Coordinates c = customizedLocation.getCoordinates();
        LatLng cur = new LatLng(c.getLatitude(), c.getLongitude());
        googleMap.addMarker(new MarkerOptions()
                .position(cur)
                .title(customizedLocation.getName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15f));
    }

    private CustomizedLocation getCustomizedLocation() {
        return (CustomizedLocation) getIntent().getSerializableExtra("CustomizedLocation");
    }

    private List<Sport> testSelectSportAll() {
        dbHelper.openDatabase();
        List<Sport> sports = dbHelper.getSports();
        dbHelper.closeDatabase();
        return sports;
    }

    private void initView() {
        setContentView(R.layout.activity_check_in_customized);
        selectFacilityButton= findViewById(R.id.select_facility);
        recyclerView = findViewById(R.id.check_in_sport_recycler);
        checkInSportButton = findViewById(R.id.check_in_sport_button);
        TextView locationView = findViewById(R.id.location_view);
        locationView.setText(getString(R.string.customized_location));
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        dbHelper = new SportAndFacilityDBHelper(this);

    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(CheckInCustomizedActivity.this, LinearLayoutManager.VERTICAL, false));
        checkInSportAdapter = new CheckInSportAdapter(CheckInCustomizedActivity.this, testSelectSportAll());
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
        checkInSportButton.setOnClickListener(view -> {
            if (ChosenSport == null) {
                Toast.makeText(
                        CheckInCustomizedActivity.this,
                        "Please select a sport.",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                Intent intent = new Intent(CheckInCustomizedActivity.this, ExerciseActivity.class);
                intent.putExtra("ChosenSport", ChosenSport);
                intent.putExtra("ChosenLocation", customizedLocation);
                startActivity(intent);
                finish();
            }
        });

        selectFacilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToCheckIn = new Intent(CheckInCustomizedActivity.this, CheckInNormalActivity.class);
                intentToCheckIn.putExtra("ClosestFacility", facility);
                intentToCheckIn.putExtra("FacilityByDistance", (Serializable) facilityList);
                intentToCheckIn.putExtra("latitude1", customizedLocation.getLatitude());
                intentToCheckIn.putExtra("longitude1", customizedLocation.getLongitude());
                startActivity(intentToCheckIn);
                finish();
            }
        });
    }

    private Facility getFacility() {
        return (Facility) getIntent().getSerializableExtra("ClosestFacility");
    }

    private List<Facility> getFacilityList() {
        return (List<Facility>) getIntent().getSerializableExtra("FacilityByDistance");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ChosenSport = checkInSportAdapter.finalChoice;
        Toast.makeText(CheckInCustomizedActivity.this, String.valueOf(ChosenSport.getName()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
