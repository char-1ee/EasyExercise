package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.CustomizedLocation;

public class NoFacilityActivity extends AppCompatActivity {
    private Button ProceedButton, CancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initButton();

    }

    private CustomizedLocation testLocation(){
        return new CustomizedLocation(new Coordinates(10,10));
    }

    private void initButton(){
        ProceedButton.setOnClickListener(v -> {
            Intent intent= new Intent(NoFacilityActivity.this, CheckInCustomizedActivity.class);
            intent.putExtra("CustomizedLocation",testLocation());
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
    }

}