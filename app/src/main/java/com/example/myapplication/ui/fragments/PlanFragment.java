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
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.SportType;
import com.example.myapplication.beans.WorkoutRecord;
import com.example.myapplication.ui.adapters.PlanRecyclerViewAdapter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanFragment extends Fragment {
    private View view;
    private List<WorkoutRecord> mWorkoutHistory;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new PlanRecyclerViewAdapter(getListData());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private List<WorkoutRecord> getListData() {
        mWorkoutHistory = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Sport sport = new Sport("Swimming", R.drawable.swimming, SportType.INDOOR_OUTDOOR);
            List<Sport> mSportList = new ArrayList<>();
            mSportList.add(new Sport("Swimming", R.drawable.swimming, SportType.INDOOR_OUTDOOR));
            Facility facility = new Facility(
                    new Coordinates(0, 0),
                    "North Hill",
                    "https://www.ntu.edu.sg",
                    "84073568",
                    "64 Nanyang Cres, Singapore 636959",
                    R.drawable.tanjong,
                    mSportList);
            Date date = new Date(2021, 9, 19);
            Time time = new Time(10, 35, 4);
        //    mWorkoutHistory.add(new WorkoutHistoryItem(sport, facility, 24, time, date));
        }
        return mWorkoutHistory;
    }
}
