package com.example.myapplication.ui.ViewPlan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.classes.Facility;
import com.example.myapplication.classes.Sport;
import com.example.myapplication.classes.WorkoutHistory;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewPlan extends AppCompatActivity {

    private List<WorkoutHistory> mWorkOutHistoryList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plan);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new PlanRecyclerViewAdapter(getListData());
        LinearLayoutManager manager = new LinearLayoutManager(ViewPlan.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

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