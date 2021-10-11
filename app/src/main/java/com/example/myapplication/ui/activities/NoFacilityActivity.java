package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.CustomizedLocation;
import com.example.myapplication.beans.Location;

public class NoFacilityActivity extends AppCompatActivity {

    Button ProceedButton, CancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_facility);
        ProceedButton= findViewById(R.id.proceed_button);
        CancelButton= findViewById(R.id.cancel_button);

        ProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NoFacilityActivity.this, CheckInNormalActivity.class);
                intent.putExtra("CustomizedLocation",testLocation());
                startActivity(intent);
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NoFacilityActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private CustomizedLocation testLocation(){
        CustomizedLocation l= new CustomizedLocation(new Coordinates(10,10));
        return l;
    }

}