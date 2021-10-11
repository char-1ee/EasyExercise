package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;

import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity {
    private ImageView profileView, sportView;
    private Button shareButton;
    private TextView timeDuration;
    private Sport sport;
    private Facility facility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        sport= getSport();
        facility= getFacility();
        shareButton=findViewById(R.id.shareButton);
        profileView=findViewById(R.id.checkoutProfile);
        timeDuration = findViewById(R.id.time_duration);
        sportView.setImageResource(sport.getImage());
        timeDuration.setText(getTimeDuration());
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
        String s= (String) getIntent().getExtras().getString("timeDuration");
        return s;
    }

}