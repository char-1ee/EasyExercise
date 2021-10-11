package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;

public class CheckOutActivity extends AppCompatActivity {
    private ImageView profileView, sportView;
    private Button exitButton;
    private TextView timeDuration;
    private Sport sport;
    private Facility facility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        sport= getSport();
        facility= getFacility();
        sportView=findViewById(R.id.checkoutPic);
        exitButton=findViewById(R.id.exitButton);
        profileView=findViewById(R.id.checkoutProfile);
        timeDuration = findViewById(R.id.time_duration);
        sportView.setImageResource(sport.getImage());
        timeDuration.setText(getTimeDuration());


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( CheckOutActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private Facility getFacility(){
        Facility f= (Facility) getIntent().getSerializableExtra("FacilityExercise");
        return f;
    }

    private Sport getSport(){
        Sport s= (Sport) getIntent().getSerializableExtra("SportExercise");
        return s;
    }

    private String getTimeDuration(){
        String s= (String) getIntent().getSerializableExtra("timeDuration");
        return s;
    }

}