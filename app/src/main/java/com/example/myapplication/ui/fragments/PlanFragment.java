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
import com.example.myapplication.databases.WorkoutPlanQueryImp;
import com.example.myapplication.ui.adapters.PlanRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * The fragment class for showing all existing plan in the local database.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class PlanFragment extends Fragment {
    private List<WorkoutPlan> mWorkoutPlan;
    private RecyclerView mRecyclerView;
    private WorkoutPlanQueryImp workoutPlanQueryImp;

    public PlanFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        //workoutPlanQueryImp = new WorkoutPlanQueryImp();
        mRecyclerView = view.findViewById(R.id.recycler_view);
        initAdapter();
        return view;
    }

    private WorkoutPlan getListData() {
        Sport s = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Location location = testCheckinClosetFacility();
        return new WorkoutPlan(s, location, 0, WorkoutPlan.WorkoutPlanStatus.PRIVATE);
    }

    private Facility testCheckinClosetFacility() {
        Sport a = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b = new Sport(0, "Running", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c = new Sport(0, "Basketball", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Facility r = new Facility(0, "wave", "http://www.ringoeater.com/", "84073568", "64 Nanyang Cres", "nonononono", new Coordinates(0, 0));
        r.addSport(a);
        r.addSport(b);
        r.addSport(c);
        return r;
    }

    private List<WorkoutPlan> getListData2(WorkoutPlan w){
        List<WorkoutPlan> plan= new ArrayList<>();
        for(int i= 0; i< 5; i++){
            plan.add(w);
        }
        return plan;
    }

    /**
     * Initialize adapter for recyclerview.
     *
     */
    private void initAdapter(){
        //mAdapter = new PlanRecyclerViewAdapter(getContext(), workoutPlanQueryImp.getWorkoutPlanList(getContext()));
        RecyclerView.Adapter mAdapter = new PlanRecyclerViewAdapter(getContext(), getListData2(getListData()));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
