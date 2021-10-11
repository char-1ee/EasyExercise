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
import com.example.myapplication.beans.CustomizedLocation;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.ui.adapters.CheckInSportAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckInCustomizedActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button button1;
    private RecyclerView rv_test;
    private CustomizedLocation customizedLocation;
    private Sport ChosenSport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        customizedLocation= getCustomizedLocation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_normal);
        rv_test = findViewById(R.id.check_in_sport_recycler_view);
        button1 = findViewById(R.id.check_in_sport_choice_button);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.panorama);

        // RecyclerView adapter
        rv_test.setLayoutManager(new LinearLayoutManager(CheckInCustomizedActivity.this, LinearLayoutManager.VERTICAL, false));
        CheckInSportAdapter firstAdapter = new CheckInSportAdapter(CheckInCustomizedActivity.this, testSelectSportAll());
        // TODO: 2021/10/11 pass all sports to custom location
        rv_test.setAdapter(firstAdapter);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CheckInCustomizedActivity.this, "Option " + firstAdapter.finalChoice.getName() + " selected", Toast.LENGTH_SHORT).show();
                ChosenSport = firstAdapter.finalChoice;
                Intent intent= new Intent(CheckInCustomizedActivity.this, ExerciseActivity.class);
                intent.putExtra("ChosenSport", ChosenSport);
                intent.putExtra("ChosenFacility", customizedLocation);
                startActivity(intent);
            }
        });

    }



    private CustomizedLocation getCustomizedLocation(){
        CustomizedLocation c= (CustomizedLocation) getIntent().getSerializableExtra("CustomizedLocation");
        return c;
    }

    private List<Sport> testSelectSportAll(){
        List<Sport> sports= new ArrayList<>();
        Sport a= new Sport("swimming", R.drawable.swimming, com.example.myapplication.beans.SportType.INDOOR);
        Sport b= new Sport("running", R.drawable.run, com.example.myapplication.beans.SportType.OUTDOOR);
        Sport c= new Sport("basketball", R.drawable.basketball, com.example.myapplication.beans.SportType.OUTDOOR);
        sports.add(a);
        sports.add(b);
        sports.add(c);
        return sports;
    }


}
