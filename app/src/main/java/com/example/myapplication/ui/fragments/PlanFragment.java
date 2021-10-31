package com.example.myapplication.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.myapplication.beans.Workout;
import com.example.myapplication.beans.WorkoutPlan;
import com.example.myapplication.databases.DatabaseManager;
import com.example.myapplication.databases.WorkoutDatabaseManager;
import com.example.myapplication.ui.activities.ChatRoomActivity;
import com.example.myapplication.ui.activities.ViewPlanActivity;
import com.example.myapplication.ui.adapters.PlanRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * The fragment class for showing all existing plan in the local database.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class PlanFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<Workout> planList = new ArrayList<>();
    private PlanRecyclerViewAdapter adapter;

    public PlanFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://cz2006-9c928-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference mDatabase = database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference publicPlanDB = database.getReference().child("community");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new PlanRecyclerViewAdapter(planList, workout -> {
            //on click event
            Intent intent;
            if(workout.getStatus() == Workout.WorkoutStatus.PRIVATE){
                intent = new Intent(getContext().getApplicationContext(), ViewPlanActivity.class);
                intent.putExtra("plan", workout);
            }else{
                intent = new Intent(getContext().getApplicationContext(), ChatRoomActivity.class);
                intent.putExtra("plan", workout.getPlanID());
            }
            startActivity(intent);

        });

        mRecyclerView.setAdapter(adapter);

        mDatabase.child("WorkoutPlan").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                for (DataSnapshot s : task.getResult().getChildren()) {
                    WorkoutDatabaseManager.FirebaseWorkoutPlan receivePlan = s.getValue(WorkoutDatabaseManager.FirebaseWorkoutPlan.class);
                    if (receivePlan == null) {
                        Log.e("firebase", "Data = null", task.getException());
                    } else {
                        planList.add(WorkoutDatabaseManager.toWorkoutPlan(receivePlan, getActivity()));
                        adapter.notifyItemInserted(planList.size() - 1);
                    }
                }
            }
        });

        mDatabase.child("PublicPlan").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                for (DataSnapshot s : task.getResult().getChildren()) {
                    String planID = s.getValue(String.class);
                    if (s == null) {
                        Log.e("firebase", "Data = null", task.getException());
                    } else {
                        Log.e("test", "public:"+planID);
                        publicPlanDB.get().addOnCompleteListener(t -> {
                            if (!t.isSuccessful()) {
                                Log.e("firebase", "Error getting data", t.getException());
                            } else {
                                for(DataSnapshot g : t.getResult().getChildren()) {
                                    WorkoutDatabaseManager.FirebasePublicPlan receivePlan = g.getValue(WorkoutDatabaseManager.FirebasePublicPlan.class);
                                    Log.e("test", receivePlan.getPlan());
                                    if (receivePlan == null) {
                                        Log.e("firebase", "Data = null", t.getException());
                                    } else if(receivePlan.getPlan().equals(planID)){
                                        Log.e("test", "public:" + receivePlan.getPlan());
                                        planList.add(WorkoutDatabaseManager.toPublicPlan(receivePlan, getActivity()));
                                        adapter.notifyItemInserted(planList.size() - 1);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });

        return view;
    }

    private WorkoutPlan getListData() {
        Sport s = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Location location = testCheckInClosetFacility();
        //TODO CHANGES!!!!!!!!!!!!!!!
        return new WorkoutPlan(s, location, Workout.WorkoutStatus.PRIVATE, "");
    }

    private Facility testCheckInClosetFacility() {
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

}
