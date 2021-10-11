package com.example.myapplication.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.ui.activities.CheckInActivity;
import com.example.myapplication.ui.activities.SelectSportActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public Facility closestFacility;
    public List<Facility> FacilityByDistance;
    View view;
    Button mMakePlanButton;
    Button mCheckInButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mMakePlanButton = view.findViewById(R.id.home_plan_button);
        mCheckInButton = view.findViewById(R.id.home_checkin_button);

        mMakePlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectSportActivity.class);
                // TODO: 2021/10/11 give two sport lists, one is recommended, one is not
                intent.putExtra("RecommendedSports",(Serializable) testSelectSportRecommended());
                intent.putExtra("OtherSports",(Serializable) testSelectSportOther());
                startActivity(intent);
            }
        });
        mCheckInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2021/10/11 give the closestfacility(one) and a list of facilities sorted by distance
                Intent intent = new Intent(getActivity(), CheckInActivity.class);
                intent.putExtra("ClosestFacility",testCheckinClosetFacility());
                intent.putExtra("FacilityByDistance",(Serializable)testCheckinFacilitByDistance());
                startActivity(intent);
            }
        });

        return view;
    }

    private Facility testCheckinClosetFacility(){
        List<Sport> sports= new ArrayList<>();
        Sport a= new Sport("swimming", R.drawable.swimming, com.example.myapplication.beans.SportType.INDOOR);
        Sport b= new Sport("running", R.drawable.run, com.example.myapplication.beans.SportType.OUTDOOR);
        sports.add(a);
        sports.add(b);
        Facility f= new Facility( new Coordinates(1.290270, 103.851959), "northilla", "http://www.ringoeater.com/", "84073568","64 Nanyang Cres", R.drawable.tanjong, sports);
        return f;
    }

    private List<Facility> testCheckinFacilitByDistance(){
        List<Sport> sports= new ArrayList<>();
        Sport a= new Sport("swimming", R.drawable.swimming, com.example.myapplication.beans.SportType.INDOOR);
        Sport b= new Sport("running", R.drawable.run, com.example.myapplication.beans.SportType.OUTDOOR);
        Sport c= new Sport("running", R.drawable.run, com.example.myapplication.beans.SportType.OUTDOOR);
        sports.add(a);
        sports.add(b);
        sports.add(c);
        Facility r= new Facility( new Coordinates(0, 0), "wave", "http://www.ringoeater.com/", "84073568","64 Nanyang Cres", R.drawable.wave, sports);
        List<Facility> f = new ArrayList<Facility>();
        f.add(testCheckinClosetFacility());
        f.add(r);
        f.add(testCheckinClosetFacility());
        f.add(r);
        f.add(testCheckinClosetFacility());
        f.add(r);
        return f;
    }

    private List<Sport> testSelectSportRecommended(){
        List<Sport> sports= new ArrayList<>();
        Sport a= new Sport("swimming", R.drawable.swimming, com.example.myapplication.beans.SportType.INDOOR);
        Sport b= new Sport("running", R.drawable.run, com.example.myapplication.beans.SportType.OUTDOOR);
        Sport c= new Sport("basketball", R.drawable.basketball, com.example.myapplication.beans.SportType.OUTDOOR);
        sports.add(a);
        sports.add(b);
        sports.add(c);
        return sports;
    }

    private List<Sport> testSelectSportOther(){
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
