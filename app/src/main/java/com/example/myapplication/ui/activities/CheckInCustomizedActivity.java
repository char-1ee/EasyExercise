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
import com.example.myapplication.beans.Sport;
import com.example.myapplication.ui.adapters.CheckInSportAdapter;

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
        setContentView(R.layout.activity_check_in_customized);
        rv_test = findViewById(R.id.check_in_sport_recycler);
        button1 = findViewById(R.id.check_in_sport_button);
        imageView = findViewById(R.id.imageView5);
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
                intent.putExtra("ChosenLocation", customizedLocation);
                startActivity(intent);
                finish();
            }
        });

    }



    private CustomizedLocation getCustomizedLocation(){
        CustomizedLocation c= (CustomizedLocation) getIntent().getSerializableExtra("CustomizedLocation");
        return c;
    }

    private List<Sport> testSelectSportAll(){
        List<Sport> sports= new ArrayList<>();
        Sport a= new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b= new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c= new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        sports.add(a);
        sports.add(b);
        sports.add(c);
        return sports;
    }


}
