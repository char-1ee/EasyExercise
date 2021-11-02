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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.PrivateWorkoutPlan;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.WorkoutDatabaseManager;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters.AddPlanAdapter;

/**
 * The activity class for adding exercise plan in the making plan task.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */
public class AddPlanActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnMapReadyCallback {
    private Button buttonPublish;
    private RecyclerView recyclerView;
    private Facility facility;
    private AddPlanAdapter addPlanAdapter;
    private Sport finalSport;
    private PrivateWorkoutPlan workoutPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initMap();
        initAdapter();
        initButton();
    }

    private Facility getFacility() {
        return (Facility) getIntent().getSerializableExtra("ChosenFacility");
    }

    /**
     * Set google map and add marker for the designated location
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
        setContentView(R.layout.activity_add_plan);
        facility = getFacility();
        recyclerView = findViewById(R.id.check_in_sport_recycler);
        buttonPublish = findViewById(R.id.publish_plan_button);
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

    /**
     * Initialize map fragment for displaying google map.
     */
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(AddPlanActivity.this, LinearLayoutManager.VERTICAL, false));
        addPlanAdapter = new AddPlanAdapter(AddPlanActivity.this, facility);
        recyclerView.setAdapter(addPlanAdapter);
        addPlanAdapter.setOnItemClickListener(this);
    }

    private void initButton() {
        buttonPublish.setOnClickListener(view -> {
            if (finalSport == null) {
                Toast.makeText(
                        AddPlanActivity.this,
                        "Please select a sport.",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                workoutPlan = new PrivateWorkoutPlan(finalSport, facility, "");
                FirebaseDatabase database = FirebaseDatabase.getInstance(getString(R.string.firebase_database));
                DatabaseReference mDatabase =
                        database.getReference()
                                .child("user")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("WorkoutPlan");
                String postId = mDatabase.push().getKey();
                WorkoutDatabaseManager.FirebasePrivateWorkoutPlan firebasePlan =
                        new WorkoutDatabaseManager.FirebasePrivateWorkoutPlan(workoutPlan, postId);
                assert postId != null;
                mDatabase.child(postId).setValue(firebasePlan);

                Intent intent = new Intent(AddPlanActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        finalSport = addPlanAdapter.finalChoice;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}