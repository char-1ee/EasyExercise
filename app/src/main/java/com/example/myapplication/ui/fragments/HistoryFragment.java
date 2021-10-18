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
import com.example.myapplication.ui.adapters.HistoryRecyclerViewAdapter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryFragment extends Fragment {
    private List<WorkoutRecord> mWorkoutHistory;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new HistoryRecyclerViewAdapter(getListData());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private List<WorkoutRecord> getListData() {
        mWorkoutHistory = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            Sport sport = new Sport("Running", R.drawable.run, SportType.OUTDOOR);
//            List<Sport> sportList = new ArrayList<>();
//            sportList.add(sport);
//            Facility facility = new Facility(
//                    new Coordinates(0, 0),
//                    "North Hill",
//                    "https://www.ntu.edu.sg",
//                    "84073568",
//                    "64 Nanyang Cres, Singapore 636959",
//                    R.drawable.tanjong,
//                    sportList);
//            Date date = new Date(2021, 9, 19);
//            Time time = new Time(10, 35, 4);
//            //mWorkoutHistory.add(new WorkoutHistoryItem(sport, facility, time, date));
//        }
        return mWorkoutHistory;
    }
}
