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
import com.example.myapplication.beans.SportType;
import com.example.myapplication.ui.adapters.AddPlanAdapter;

import java.util.ArrayList;

public class AddPlanActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button button;
    private RecyclerView rv_test;
    private Facility facility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        facility= getFacility();

        rv_test = findViewById(R.id.check_in_sport_recycler_view);
        button = findViewById(R.id.add_plan_button);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(facility.getImage());

        // RecyclerView adapter
        rv_test.setLayoutManager(new LinearLayoutManager(AddPlanActivity.this, LinearLayoutManager.VERTICAL, false));
        AddPlanAdapter firstAdapter = new AddPlanAdapter(AddPlanActivity.this, facility);
        rv_test.setAdapter(firstAdapter);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddPlanActivity.this, "Option " + firstAdapter.getFinalChoice().getName() + " selected", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(AddPlanActivity.this, MainActivity.class);
                // TODO: 2021/10/11 add workout plan before returning
                startActivity(intent);
            }
        });
    }

    private Facility getFacility(){
        Facility f= (Facility) getIntent().getSerializableExtra("ChosenFacility");
        return f;
    }
}