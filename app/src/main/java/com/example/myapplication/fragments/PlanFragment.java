package com.example.myapplication.fragments;

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
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutHistory;
import com.example.myapplication.adapters.PlanRecyclerViewAdapter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanFragment extends Fragment {
    private View v;
    private List<WorkoutHistory> mWorkOutHistoryList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public PlanFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_plan, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mAdapter = new PlanRecyclerViewAdapter(getListData());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }

    private List<WorkoutHistory> getListData() {
        mWorkOutHistoryList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Sport sport = new Sport("Swimming", R.drawable.swimming, false);
            List<Sport> mSportList = new ArrayList<>();
            mSportList.add(new Sport("Swimming", R.drawable.swimming, true));
            Facility facility = new Facility("North Hill", "84073568", "64 Nanyang Cres, Singapore 636959", R.drawable.tanjong, mSportList);
            Date date = new Date(2021, 9, 19);
            Time time = new Time(10, 35, 4);
            WorkoutHistory workOutHistory = new WorkoutHistory(sport, facility, 24, time, true, date);
            mWorkOutHistoryList.add(workOutHistory);
        }
        return mWorkOutHistoryList;
    }
}
