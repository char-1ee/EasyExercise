package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.CustomizedLocation;

/**
 * The activity class for showing up message for no facility around in the checking in task.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class NoFacilityActivity extends AppCompatActivity {
    double latitude= 0;
    double longitude= 0;
    private ActionBar actionBar;
    private Button ProceedButton, CancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initButton();

    }

    private void initButton(){
        ProceedButton.setOnClickListener(v -> {
            Intent intent= new Intent(NoFacilityActivity.this, CheckInCustomizedActivity.class);
            intent.putExtra("CustomizedLocation", new CustomizedLocation(new Coordinates(latitude, longitude, "Customized Location")));
            startActivity(intent);
            finish();
        });

        CancelButton.setOnClickListener(v -> {
            Intent intent= new Intent(NoFacilityActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initView(){
        setContentView(R.layout.activity_no_facility);
        ProceedButton= findViewById(R.id.proceed_button);
        CancelButton= findViewById(R.id.cancel_button);
        latitude= getLatitude();
        longitude= getLongitude();
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public double getLatitude() {
        return (double) getIntent().getSerializableExtra("latitude1");
    }

    public double getLongitude() {
        return (double) getIntent().getSerializableExtra("longitude1");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

}