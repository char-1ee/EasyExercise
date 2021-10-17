package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class NoFacilityActivity extends AppCompatActivity {

    Button ProceedButton, CancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_facility);
        ProceedButton = findViewById(R.id.proceed_button);
        CancelButton = findViewById(R.id.cancel_button);

        ProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoFacilityActivity.this, SelectSportActivity.class);
                startActivity(intent);
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoFacilityActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}