package com.example.myapplication.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.PublicPlan;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.ui.adapters.SportRecyclerViewAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
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


                // TODO: 2021/10/1 Add condition: if facility, to selectFacility; else, to AddPlan

                /* Original Code
                Context context = SelectSportActivity.this;
                Class destinationActivity = SelectFacilityPlanActivity.class;
                Intent startChildActivityIntent = new Intent(context, destinationActivity);
                startActivity(startChildActivityIntent);
                 */

                // For testing purpose I change the function of this button. Should be changed later.
                // TODO: 2021/10/18 Change the function back

                // For test purpose, click to add a public plan

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://ontology-5ae5d-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference mDatabase = database.getReference().child("community");

                PublicPlan plan = new PublicPlan(10001, 8, new Date(), new Date(), 1, 1);
                String id = mDatabase.push().getKey();
                plan.setPlan(id);
                mDatabase.child(id).setValue(plan);
            }
        });
    }

    private List<Sport> getListData() {
        mSportList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
//            mSportList.add(new Sport("Swimming", R.drawable.swimming, SportType.INDOOR_OUTDOOR));
        }
        return mSportList;
    }
}