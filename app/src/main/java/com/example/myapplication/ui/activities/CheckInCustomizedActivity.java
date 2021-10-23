package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.CustomizedLocation;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.databases.DBManager;
import com.example.myapplication.ui.adapters.CheckInSportAdapter;

import java.util.ArrayList;
import java.util.List;

public class CheckInCustomizedActivity extends AppCompatActivity {
    private DBManager db;
    private ImageView imageView;
    private Button button1;
    private RecyclerView rv_test;
    private CustomizedLocation customizedLocation;
    private Sport ChosenSport;
    private TextView locationView;
    private CheckInSportAdapter firstAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        customizedLocation= getCustomizedLocation();
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
        initButton();

    }

    private CustomizedLocation getCustomizedLocation(){
        return (CustomizedLocation) getIntent().getSerializableExtra("CustomizedLocation");
    }

    private List<Sport> testSelectSportAll(){
        db.openDatabase();
        List<Sport> sports= db.getSports();
        db.closeDatabase();
        return sports;
    }

    private void initView(){
        setContentView(R.layout.activity_check_in_customized);
        rv_test = findViewById(R.id.check_in_sport_recycler);
        button1 = findViewById(R.id.check_in_sport_button);
        imageView = findViewById(R.id.imageView5);
        imageView.setImageResource(R.drawable.panorama);
        locationView= findViewById(R.id.location_view);
        locationView.setText(getString(R.string.customized_location));
        db= new DBManager(this);
    }

    private void initAdapter(){
        rv_test.setLayoutManager(new LinearLayoutManager(CheckInCustomizedActivity.this, LinearLayoutManager.VERTICAL, false));
        firstAdapter = new CheckInSportAdapter(CheckInCustomizedActivity.this, testSelectSportAll());
        rv_test.setAdapter(firstAdapter);
        rv_test.post(new Runnable()
        {
            @Override
            public void run() {
                firstAdapter.notifyDataSetChanged();
            }
        });

    }

    private void initButton(){
        button1.setOnClickListener(view -> {
            Toast.makeText(CheckInCustomizedActivity.this, "Option " + firstAdapter.finalChoice.getName() + " selected", Toast.LENGTH_SHORT).show();
            ChosenSport = firstAdapter.finalChoice;
            Intent intent= new Intent(CheckInCustomizedActivity.this, ExerciseActivity.class);
            intent.putExtra("ChosenSport", ChosenSport);
            intent.putExtra("ChosenLocation", customizedLocation);
            startActivity(intent);
            finish();
        });
    }
}
