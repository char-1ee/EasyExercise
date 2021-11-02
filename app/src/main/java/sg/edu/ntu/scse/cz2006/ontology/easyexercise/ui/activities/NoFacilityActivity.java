package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.CustomizedLocation;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;

/**
 * The activity class for showing up message for no facility around in the checking in task.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class NoFacilityActivity extends AppCompatActivity {
    private Facility facility;
    private List<Facility> facilityList;
    private double latitude = 0;
    private double longitude = 0;
    private Button buttonProceed;
    private Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initButton();

    }

    private void initButton() {
        buttonProceed.setOnClickListener(v -> {
            Intent intent = new Intent(NoFacilityActivity.this, CheckInCustomizedActivity.class);
            intent.putExtra("ClosestFacility", facility);
            intent.putExtra("FacilityByDistance", (Serializable) facilityList);
            intent.putExtra("CustomizedLocation", new CustomizedLocation(new Coordinates(latitude, longitude, "Customized Location")));
            startActivity(intent);
            finish();
        });

        buttonCancel.setOnClickListener(v -> {
            Intent intent = new Intent(NoFacilityActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initView() {
        facility = getFacility();
        facilityList = getFacilityList();
        setContentView(R.layout.activity_no_facility);
        buttonProceed = findViewById(R.id.proceed_button);
        buttonCancel = findViewById(R.id.cancel_button);
        latitude = getLatitude();
        longitude = getLongitude();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public double getLatitude() {
        return (double) getIntent().getSerializableExtra("latitude1");
    }

    public double getLongitude() {
        return (double) getIntent().getSerializableExtra("longitude1");
    }

    private Facility getFacility() {
        return (Facility) getIntent().getSerializableExtra("ClosestFacility");
    }

    private List<Facility> getFacilityList() {
        return (List<Facility>) getIntent().getSerializableExtra("FacilityByDistance");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
