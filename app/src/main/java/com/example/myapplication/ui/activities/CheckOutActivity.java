package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.sportsImage.SportsImage;

import java.util.Date;

public class CheckOutActivity extends AppCompatActivity {
    private ImageView profileView, sportView;
    private Button exitButton;
    private TextView timeDuration;
    private Sport sport;
    private Location location;
    private Date startDate, endDate, diff;
    private SportsImage sm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initButton();
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
        sportView=findViewById(R.id.checkoutPic);
        exitButton=findViewById(R.id.exitButton);
        profileView=findViewById(R.id.checkoutProfile);
        timeDuration = findViewById(R.id.time_duration);
        sportView.setImageResource(sm.SportsToImage(sport));
        timeDuration.setText(getTimeDuration());
    }

    private void initButton(){
        exitButton.setOnClickListener(view -> {
            Intent intent= new Intent(CheckOutActivity.this, MainActivity.class);
            // TODO: 2021/10/11 add exercise record with facility, sport, endtime and starttime
            startActivity(intent);
            finish();
        });
    }

}