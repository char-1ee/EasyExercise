package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.fragments;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Location;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.PrivateWorkoutPlan;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Workout;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.WorkoutDatabaseManager;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.ChatRoomActivity;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.ViewPlanActivity;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters.PlanRecyclerViewAdapter;

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

        FirebaseDatabase database = FirebaseDatabase.getInstance(getContext().getString(R.string.firebase_database));
        DatabaseReference mDatabase = database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference publicPlanDB = database.getReference().child("community");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new PlanRecyclerViewAdapter(planList, workout -> {
            //on click event
            Intent intent;
            if (workout.getStatus() == Workout.WorkoutStatus.PRIVATE) {
                intent = new Intent(getContext().getApplicationContext(), ViewPlanActivity.class);
                intent.putExtra("plan", workout);
            } else {
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
                    WorkoutDatabaseManager.FirebasePrivateWorkoutPlan receivePlan = s.getValue(WorkoutDatabaseManager.FirebasePrivateWorkoutPlan.class);
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
                        Log.e("test", "public:" + planID);
                        publicPlanDB.get().addOnCompleteListener(t -> {
                            if (!t.isSuccessful()) {
                                Log.e("firebase", "Error getting data", t.getException());
                            } else {
                                for (DataSnapshot g : t.getResult().getChildren()) {
                                    WorkoutDatabaseManager.FirebasePublicWorkoutPlan receivePlan = g.getValue(WorkoutDatabaseManager.FirebasePublicWorkoutPlan.class);
                                    // Log.e("test", receivePlan.getPlanID());
                                    if (receivePlan == null) {
                                        Log.e("firebase", "Data = null", t.getException());
                                    } else if (receivePlan.getPlanID().equals(planID)) {
                                        Log.e("test", "public:" + receivePlan.getPlanID());
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

    private PrivateWorkoutPlan getListData() {
        Sport s = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Location location = testCheckInClosetFacility();
        //TODO CHANGES!!!!!!!!!!!!!!!
        return new PrivateWorkoutPlan(s, location, "");
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

    private List<PrivateWorkoutPlan> getListData2(PrivateWorkoutPlan w) {
        List<PrivateWorkoutPlan> plan = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            plan.add(w);
        }
        return plan;
    }

}
