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
import com.example.myapplication.beans.Sport;
import com.example.myapplication.ui.adapters.SportRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectSportActivity extends AppCompatActivity {
    private Button mSportChoicesConfirmButton;
    private List<Sport> mSportList;
    private RecyclerView mRecyclerView, mRecyclerView2;
    private RecyclerView.Adapter mAdapter, mAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sport);

        mSportChoicesConfirmButton = (Button) findViewById(R.id.sport_choices_confirm_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new SportRecyclerViewAdapter(getListData());
        LinearLayoutManager manager = new GridLayoutManager(SelectSportActivity.this, 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        mAdapter2 = new SportRecyclerViewAdapter(getListData());
        LinearLayoutManager manager2 = new GridLayoutManager(SelectSportActivity.this, 2);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(manager2);
        mRecyclerView2.setAdapter(mAdapter2);

        mSportChoicesConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = SelectSportActivity.this;
                Class destinationActivity = SelectFacilityActivity.class;
                Intent startChildActivityIntent = new Intent(context, destinationActivity);
                startActivity(startChildActivityIntent);
            }
        });
    }

    private List<Sport> getListData() {
        mSportList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            mSportList.add(new Sport("Swimming", R.drawable.swimming, true));
        }
        return mSportList;
    }
}