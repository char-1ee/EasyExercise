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
import com.example.myapplication.beans.WorkoutRecord;
import com.example.myapplication.ui.adapters.HistoryRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The fragment class for showing all existing history.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class HistoryFragment extends Fragment {
    private RecyclerView mRecyclerView;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        initAdapter();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private List<WorkoutRecord> getListData() {
        List<WorkoutRecord> mWorkoutHistory = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Sport s = new Sport(0, "Free play", "swimming", Sport.SportType.INDOOR_OUTDOOR);
            Location location = testCheckinClosetFacility();
            Date date = new Date();
            // TODO: Update workout record!!!!!!!!
            WorkoutRecord w = new WorkoutRecord(s, location, "", date, date);
            mWorkoutHistory.add(w);
            Sport s2 = new Sport(0, "Football", "swimming", Sport.SportType.INDOOR_OUTDOOR);
            Location location2 = testCheckinClosetFacility();
            Date date2 = new Date();
            // TODO: Update workout record!!!!!!!!
            WorkoutRecord w2 = new WorkoutRecord(s2, location2, "", date2, date2);
            mWorkoutHistory.add(w2);
        }
        return mWorkoutHistory;
    }


    private Facility testCheckinClosetFacility() {
        Sport a = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Facility r = new Facility(0, "wave", "http://www.ringoeater.com/", "84073568", "64 Nanyang Cres", "nonononono", new Coordinates(0, 0));
        r.addSport(a);
        r.addSport(b);
        r.addSport(c);
        return r;
    }

    /**
     * Initialize adapter for recyclerview.
     *
     * @author Ruan Donglin
     */
    private void initAdapter(){
        RecyclerView.Adapter mAdapter = new HistoryRecyclerViewAdapter(getListData(), getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
