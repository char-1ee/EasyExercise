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
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.SportType;
import com.example.myapplication.ui.adapters.CheckInSportAdapter;

import java.util.ArrayList;

public class CheckInActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button button1, button2;
    private RecyclerView rv_test;
    private ArrayList<Sport> secondList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
//        secondList.add(new SportTable("Swimming", R.drawable.swimming, SportType.INDOOR_OUTDOOR));
//        secondList.add(new SportTable("Swimming", R.drawable.swimming, SportType.INDOOR_OUTDOOR));
//        secondList.add(new SportTable("Swimming", R.drawable.swimming, SportType.INDOOR_OUTDOOR));
        rv_test = findViewById(R.id.check_in_sport_recycler_view);
        button1 = findViewById(R.id.check_in_sport_choice_button);
        button2 = findViewById(R.id.choose_another_facility_button);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.swimming);

        // RecyclerView adapter
        rv_test.setLayoutManager(new LinearLayoutManager(CheckInActivity.this, LinearLayoutManager.VERTICAL, false));
        CheckInSportAdapter firstAdapter = new CheckInSportAdapter(CheckInActivity.this, secondList);
        rv_test.setAdapter(firstAdapter);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CheckInActivity.this, "Option " + firstAdapter.finalChoice.getName() + " selected", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(CheckInActivity.this, ExerciseActivity.class);
                startActivity(intent1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(CheckInActivity.this, SelectFacilityCheckInActivity.class);
                startActivity(intent2);
            }
        });

    }
}
