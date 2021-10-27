package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.sportsImage.SportsImage;

import java.util.Date;

public class CheckOutActivity extends AppCompatActivity {
    private ImageView profileView, sportView;
    private Button exitButton;
    private TextView timeDuration, placeView, sportNameView;
    private Sport sport;
    private Location location;
    private Date startDate, endDate, diff;
    private SportsImage sm;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initButton();
        // TODO: 2021/10/24  addRecord();
    }


    private Location getLocation() {
        return (Location) getIntent().getSerializableExtra("LocationExercise");
    }

    private Sport getSport() {
        return (Sport) getIntent().getSerializableExtra("SportExercise");
    }

    private String getTimeDuration() {
        return (String) getIntent().getSerializableExtra("timeDuration");
    }

    private Date getStartDate(){
        return (Date) getIntent().getSerializableExtra("StartDate");
    }

    private void initView(){
        setContentView(R.layout.activity_check_out);
        sm= new SportsImage();
        endDate= new Date();
        sport = getSport();
        location = getLocation();
        startDate = getStartDate();
        diff = new Date(endDate.getTime() - startDate.getTime());
        sportNameView= findViewById(R.id.checkoutSport);
        placeView= findViewById(R.id.checkoutPlace);
        sportView=findViewById(R.id.checkoutPic);
        exitButton=findViewById(R.id.exitButton);
        profileView=findViewById(R.id.checkoutProfile);
        profileView.setClipToOutline(true);
        timeDuration = findViewById(R.id.time_duration);
        sportView.setImageResource(sm.SportsToImage(sport));
        placeView.setText(location.getName());
        timeDuration.setText(getTimeDuration());
        sportNameView.setText(sport.getName());
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initButton(){
        exitButton.setOnClickListener(view -> {
            Intent intent= new Intent(CheckOutActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        Intent intent= new Intent(CheckOutActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        moveTaskToBack(true);
        Intent intent= new Intent(CheckOutActivity.this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}