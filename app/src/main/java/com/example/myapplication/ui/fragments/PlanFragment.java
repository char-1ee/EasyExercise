package com.example.myapplication.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutPlan;
import com.example.myapplication.ui.adapters.PlanRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment {
    private View view;
    private List<WorkoutPlan> mWorkoutPlan;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new PlanRecyclerViewAdapter(getContext(), getListData());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private List<WorkoutPlan> getListData() {
        mWorkoutPlan = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Sport s = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
            Location location = testCheckinClosetFacility();
            WorkoutPlan w = new WorkoutPlan(s, location, 0, com.example.myapplication.beans.WorkoutPlanStatus.PRIVATE);
            mWorkoutPlan.add(w);
        }
        return mWorkoutPlan;
    }

    private Facility testCheckinClosetFacility() {
        Sport a = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Facility r = new Facility(0, "wave", "http://www.ringoeater.com/", "84073568", "64 Nanyang Cres", "nonononono", new Coordinates(0, 0));
        r.addSport(a);
        r.addSport(b);
        r.addSport(c);
        return r;
    }
}
