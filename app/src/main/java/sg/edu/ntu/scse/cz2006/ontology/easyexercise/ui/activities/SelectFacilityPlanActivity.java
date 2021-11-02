package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;

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

import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters.FacilityRecyclerViewAdapterPlan;

/**
 * The activity class for showing all facility for making a workout plan, in the making plan task.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class SelectFacilityPlanActivity extends AppCompatActivity implements OnMapReadyCallback {
    double latitude = 0;
    double longitude = 0;
    private List<Facility> FacilityQualified;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GoogleMap mMap;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
        initMap();
    }


    private List<Facility> getFacilityQualified() {
        return (List<Facility>) getIntent().getSerializableExtra("FacilityQualified");
    }

    private void initView() {
        setContentView(R.layout.activity_select_facility);
        latitude = getLatitude();
        longitude = getLongitude();
        FacilityQualified = getFacilityQualified();
        mRecyclerView = findViewById(R.id.recycler_view);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mAdapter = new FacilityRecyclerViewAdapterPlan(SelectFacilityPlanActivity.this, FacilityQualified, new Coordinates(latitude, longitude, ""));
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /**
     * Initialize adapter for recyclerview.
     */
    private void initAdapter() {
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        for (Facility curFacility : FacilityQualified) {
            LatLng cur = new LatLng(curFacility.getLatitude(), curFacility.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(cur)
                    .title(curFacility.getName()));
        }
        LatLng cur = new LatLng(FacilityQualified.get(0).getLatitude(), FacilityQualified.get(0).getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 13f));
    }
}