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

import java.util.Date;

public class CheckOutActivity extends AppCompatActivity {
    private ImageView profileView, sportView;
    private Button exitButton;
    private TextView timeDuration;
    private Sport sport;
    private Facility facility;
    private Date startDate, endDate, diff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        endDate= new Date();
        sport= getSport();
        facility= getFacility();
        startDate= getStartDate();
        diff = new Date(endDate.getTime() - startDate.getTime());
        sportView=findViewById(R.id.checkoutPic);
        exitButton=findViewById(R.id.exitButton);
        profileView=findViewById(R.id.checkoutProfile);
        timeDuration = findViewById(R.id.time_duration);
        //sportView.setImageResource(sport.getImage());
        timeDuration.setText(getTimeDuration());


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( CheckOutActivity.this, MainActivity.class);
                // TODO: 2021/10/11 add exercise record with facility, sport, endtime and starttime
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

    private Date getStartDate(){
        Date d= (Date) getIntent().getSerializableExtra("StartDate");
        return d;
    }

}