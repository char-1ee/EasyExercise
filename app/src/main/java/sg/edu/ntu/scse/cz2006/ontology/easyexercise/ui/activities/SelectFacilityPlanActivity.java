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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    private List<Facility> facilities;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
        initMap();
    }


    private List<Facility> getFacilities() {
        return (List<Facility>) getIntent().getSerializableExtra("FacilityQualified");
    }

    private void initView() {
        setContentView(R.layout.activity_select_facility);
        latitude = getLatitude();
        longitude = getLongitude();
        facilities = getFacilities();
        recyclerView = findViewById(R.id.recycler_view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        adapter = new FacilityRecyclerViewAdapterPlan(
                SelectFacilityPlanActivity.this,
                facilities,
                new Coordinates(latitude, longitude));
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
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
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("My current position")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        for (Facility curFacility : facilities) {
            LatLng cur = new LatLng(curFacility.getLatitude(), curFacility.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(cur)
                    .title(curFacility.getName()));
        }
        LatLng cur = new LatLng(facilities.get(0).getLatitude(), facilities.get(0).getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 13f));
    }
}
