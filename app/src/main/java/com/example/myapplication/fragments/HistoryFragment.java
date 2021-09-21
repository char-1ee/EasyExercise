package com.example.myapplication.fragment;

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
import com.example.myapplication.bean.Facility;
import com.example.myapplication.bean.Sport;
import com.example.myapplication.bean.WorkoutHistory;
import com.example.myapplication.adapter.HistoryRecyclerViewAdapter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryFragment extends Fragment {
    private List<WorkoutHistory> mWorkOutHistoryList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    View v;

    public HistoryFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_history, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mAdapter = new HistoryRecyclerViewAdapter(getListData());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    private List<WorkoutHistory> getListData() {
        mWorkOutHistoryList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Sport sport = new Sport("Running", R.drawable.ic_baseline_directions_run_24, false);
            List<Sport> mSportList = new ArrayList<>();
            mSportList.add(new Sport("Running", R.drawable.ic_baseline_directions_run_24, true));
            Facility facility = new Facility("north hill", "84073568", "64 Nanyang Cres, Singapore 636959", R.drawable.tanjong, mSportList);
            Date date = new Date(2021, 9, 19);
            Time time = new Time(10, 35, 4);
            WorkoutHistory workOutHistory = new WorkoutHistory(sport, facility, 24, time, true, date);
            mWorkOutHistoryList.add(workOutHistory);
        }
        return mWorkOutHistoryList;
    }
}
