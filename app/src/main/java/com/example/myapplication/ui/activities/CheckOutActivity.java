package com.example.myapplication.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.sportsImage.SportsImage;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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
        Location f = (Location) getIntent().getSerializableExtra("LocationExercise");
        return f;
    }

    private Sport getSport() {
        Sport s = (Sport) getIntent().getSerializableExtra("SportExercise");
        return s;
    }

    private String getTimeDuration() {
        String s = (String) getIntent().getSerializableExtra("timeDuration");
        return s;
    }

    private Date getStartDate(){
        Date d= (Date) getIntent().getSerializableExtra("StartDate");
        return d;
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
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(CheckOutActivity.this, MainActivity.class);
                // TODO: 2021/10/11 add exercise record with facility, sport, endtime and starttime
                startActivity(intent);
                finish();
            }
        });
    }

}