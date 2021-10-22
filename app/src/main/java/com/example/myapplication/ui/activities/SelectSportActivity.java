package com.example.myapplication.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.recommendation.FacilityRecommendation;
import com.example.myapplication.ui.adapters.SportRecyclerViewAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectSportActivity extends AppCompatActivity {
    private List<Sport> finalChoice;
    TextView textView;
    private Coordinates coordinate;
    private double latitude;
    private double longitude;
    private List<Sport> ChosenSport1;
    private List<Sport> ChosenSport2;
    private List<Facility> FinalFacility;
    private List<Sport> RecommendedSport;
    private List<Sport> OtherSport;
    private Button mSportChoicesConfirmButton;
    private RecyclerView mRecyclerView, mRecyclerView2;
    private SportRecyclerViewAdapter mAdapter, mAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
        initButton();
    }

    private Coordinates getCoordinate(){
        return (Coordinates) getIntent().getSerializableExtra("Coordinate");
    }


    private List<Sport> getRecommendedSport() {
        return (List<Sport>) getIntent().getSerializableExtra("RecommendedSports");
    }

    private List<Sport> getOtherSport() {
        return (List<Sport>) getIntent().getSerializableExtra("OtherSports");
    }

    private List<Facility> testGiveFacility() {
        Sport a = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Facility r = new Facility(0, "wave", "http://www.ringoeater.com/", "84073568", "64 Nanyang Cres", "nonononono", new Coordinates(0, 0));
        r.addSport(a);
        r.addSport(b);
        r.addSport(c);
        List<Facility> f = new ArrayList<>();
        f.add(testFacility());
        f.add(r);
        f.add(testFacility());
        f.add(r);
        f.add(testFacility());
        f.add(r);
        return f;
    }

    private Facility testFacility() {
        Sport a = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Facility f = new Facility(0, "wave", "http://www.ringoeater.com/", "84073568", "64 Nanyang Cres", "nonononono", new Coordinates(0, 0));
        f.addSport(a);
        f.addSport(b);
        return f;
    }

    private void initView(){
        finalChoice= new ArrayList<>();
        setContentView(R.layout.activity_select_sport);
        RecommendedSport = getRecommendedSport();
        OtherSport = getOtherSport();
        coordinate= getCoordinate();
        textView= findViewById(R.id.textView);
        mSportChoicesConfirmButton = findViewById(R.id.sport_choices_confirm_button);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    /**
     * Initialize adapter for recyclerview.
     *
     */
    private void initAdapter(){
        mAdapter = new SportRecyclerViewAdapter(RecommendedSport);
        LinearLayoutManager manager = new GridLayoutManager(SelectSportActivity.this, 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView2 = findViewById(R.id.recycler_view2);
        mAdapter2 = new SportRecyclerViewAdapter(OtherSport);
        LinearLayoutManager manager2 = new GridLayoutManager(SelectSportActivity.this, 2);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(manager2);
        mRecyclerView2.setAdapter(mAdapter2);
    }

    private void initButton(){
        mSportChoicesConfirmButton.setOnClickListener(view -> {
            Context context = SelectSportActivity.this;
            ChosenSport1 = mAdapter.chosenSportList;
            ChosenSport2 = mAdapter2.chosenSportList;
            finalChoice.clear();
            finalChoice.addAll(ChosenSport1);
            finalChoice.addAll(ChosenSport2);
            FacilityRecommendation facilityRecommendation= new FacilityRecommendation();
            FinalFacility= facilityRecommendation.recommend(SelectSportActivity.this, finalChoice, coordinate);
            // TODO: 2021/10/11 Search qualified facilities basing on sports chosen
            // TODO: 2021/10/11 the list of sports: ChosenSports1
            //FinalFacility = testGiveFacility();
            textView.setText(String.valueOf(ChosenSport1.size()+ ChosenSport2.size()));

//            Intent intent = new Intent(context, SelectFacilityPlanActivity.class);
//            intent.putExtra("FacilityQualified", (Serializable) FinalFacility);
//            startActivity(intent);
//            finish();
        });
    }
}