package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.SportType;
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
        setContentView(R.layout.activity_select_sport);
        RecommendedSport= getRecommendedSport();
        OtherSport= getOtherSport();

        mSportChoicesConfirmButton = (Button) findViewById(R.id.sport_choices_confirm_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new SportRecyclerViewAdapter(RecommendedSport);
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

        mSportChoicesConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = SelectSportActivity.this;
                ChosenSport1= mAdapter.getChosenSportList();
                ChosenSport2= mAdapter2.getChosenSportList();
                ChosenSport1.addAll(ChosenSport2);
                // TODO: 2021/10/11 Search qualified facilities basing on sports chosen
                // TODO: 2021/10/11 the list of sports: ChosenSports1
                FinalFacility= testGiveFacility();
                Intent intent = new Intent(context, SelectFacilityPlanActivity.class);
                intent.putExtra("FacilityQualified",(Serializable) FinalFacility);
                startActivity(intent);
            }
        });
    }

    private List<Sport> getRecommendedSport(){
        List<Sport> s= (List<Sport>) getIntent().getSerializableExtra("RecommendedSports");
        return s;
    }

    private List<Sport> getOtherSport(){
        List<Sport> s= (List<Sport>) getIntent().getSerializableExtra("OtherSports");
        return s;
    }

    private List<Facility> testGiveFacility(){
        List<Facility> mFacilityList = new ArrayList<Facility>();
        List<Sport> mSportList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            mSportList.add(new Sport("Swimming", R.drawable.swimming, SportType.INDOOR_OUTDOOR));
        }
        for (int i = 1; i <= 25; i++) {
            mFacilityList.add(new Facility(
                    new Coordinates(0, 0),
                    "North Hill",
                    "https://www.ntu.edu.sg",
                    "84073568",
                    "64 Nanyang Cres, Singapore 636959",
                    R.drawable.tanjong,
                    mSportList));
        }
        return mFacilityList;
    }
}