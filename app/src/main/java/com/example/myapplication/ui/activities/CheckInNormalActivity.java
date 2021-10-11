package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.ui.adapters.CheckInSportAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckInNormalActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button button1, button2;
    private RecyclerView rv_test;
    private ArrayList<Sport> secondList = new ArrayList<>();
    private Facility facility;
    private List<Facility> facilityList;
    private Sport ChosenSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        facility= getFacility();
        facilityList= getFacilityList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_normal);
        rv_test = findViewById(R.id.check_in_sport_recycler);
        button1 = findViewById(R.id.check_in_sport_button);
        button2 = findViewById(R.id.choose_another_facility_button);
        imageView = findViewById(R.id.imageView5);
        imageView.setImageResource(facility.getImage());

        // RecyclerView adapter
        rv_test.setLayoutManager(new LinearLayoutManager(CheckInNormalActivity.this, LinearLayoutManager.VERTICAL, false));
        CheckInSportAdapter firstAdapter = new CheckInSportAdapter(CheckInNormalActivity.this, facility.getSportsSupported());
        rv_test.setAdapter(firstAdapter);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CheckInNormalActivity.this, "Option " + firstAdapter.finalChoice.getName() + " selected", Toast.LENGTH_SHORT).show();
                ChosenSport = firstAdapter.finalChoice;
                Intent intent= new Intent(CheckInNormalActivity.this, ExerciseActivity.class);
                intent.putExtra("ChosenSport", ChosenSport);
                intent.putExtra("ChosenFacility", facility);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckInNormalActivity.this, SelectFacilityCheckInActivity.class);
                intent.putExtra("FacilityByDistance2",(Serializable)facilityList);
                startActivity(intent);
            }
        });

    }


    private Facility getFacility(){
        Facility f= (Facility) getIntent().getSerializableExtra("ClosestFacility");
        return f;
    }

    private List<Facility> getFacilityList(){
        List<Facility> f= (List<Facility>) getIntent().getSerializableExtra("FacilityByDistance");
        return f;
    }

}
