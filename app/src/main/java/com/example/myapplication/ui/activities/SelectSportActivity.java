package com.example.myapplication.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.ui.adapters.SportRecyclerViewAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectSportActivity extends AppCompatActivity {
    private List<Sport> ChosenSport1;
    private List<Sport> ChosenSport2;
    private List<Facility> FinalFacility;
    private List<Sport> RecommendedSport;
    private List<Sport> OtherSport;
    private Button mSportChoicesConfirmButton;
    private List<Sport> mSportList;
    private RecyclerView mRecyclerView, mRecyclerView2;
    private SportRecyclerViewAdapter mAdapter, mAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
        initButton();
    }

    private List<Sport> getRecommendedSport() {
        List<Sport> s = (List<Sport>) getIntent().getSerializableExtra("RecommendedSports");
        return s;
    }

    private List<Sport> getOtherSport() {
        List<Sport> s = (List<Sport>) getIntent().getSerializableExtra("OtherSports");
        return s;
    }

    private List<Facility> testGiveFacility() {
        Sport a = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Facility r = new Facility(0, "wave", "http://www.ringoeater.com/", "84073568", "64 Nanyang Cres", "nonononono", new Coordinates(0, 0));
        r.addSport(a);
        r.addSport(b);
        r.addSport(c);
        List<Facility> f = new ArrayList<Facility>();
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
        setContentView(R.layout.activity_select_sport);
        RecommendedSport = getRecommendedSport();
        OtherSport = getOtherSport();
        mSportChoicesConfirmButton = (Button) findViewById(R.id.sport_choices_confirm_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new SportRecyclerViewAdapter(RecommendedSport);
    }

    private void initAdapter(){
        LinearLayoutManager manager = new GridLayoutManager(SelectSportActivity.this, 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        mAdapter2 = new SportRecyclerViewAdapter(OtherSport);
        LinearLayoutManager manager2 = new GridLayoutManager(SelectSportActivity.this, 2);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(manager2);
        mRecyclerView2.setAdapter(mAdapter2);
    }

    private void initButton(){
        mSportChoicesConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = SelectSportActivity.this;
                ChosenSport1 = mAdapter.getChosenSportList();
                ChosenSport2 = mAdapter2.getChosenSportList();
                ChosenSport1.addAll(ChosenSport2);
                // TODO: 2021/10/11 Search qualified facilities basing on sports chosen
                // TODO: 2021/10/11 the list of sports: ChosenSports1
                FinalFacility = testGiveFacility();
                Intent intent = new Intent(context, SelectFacilityPlanActivity.class);
                intent.putExtra("FacilityQualified", (Serializable) FinalFacility);
                startActivity(intent);
                finish();
            }
        });
    }
}