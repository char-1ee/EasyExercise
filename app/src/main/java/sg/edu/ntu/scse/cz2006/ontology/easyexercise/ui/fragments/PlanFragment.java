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
    private final List<Workout> workouts = new ArrayList<>();
    private PlanRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        FirebaseDatabase database = FirebaseDatabase.getInstance(getContext().getString(R.string.firebase_database));
        DatabaseReference mDatabase = database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference publicPlanDB = database.getReference().child("community");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlanRecyclerViewAdapter(workouts, workout -> {
            // on click event
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

        recyclerView.setAdapter(adapter);

        mDatabase.child("WorkoutPlan").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                for (DataSnapshot s : task.getResult().getChildren()) {
                    WorkoutDatabaseManager.FirebasePrivateWorkoutPlan receivePlan =
                            s.getValue(WorkoutDatabaseManager.FirebasePrivateWorkoutPlan.class);
                    if (receivePlan == null) {
                        Log.e("firebase", "Data = null", task.getException());
                    } else {
                        workouts.add(WorkoutDatabaseManager.toWorkoutPlan(receivePlan, getActivity()));
                        adapter.notifyItemInserted(workouts.size() - 1);
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
                    Log.e("test", "public:" + planID);
                    publicPlanDB.get().addOnCompleteListener(t -> {
                        if (!t.isSuccessful()) {
                            Log.e("firebase", "Error getting data", t.getException());
                        } else {
                            for (DataSnapshot g : t.getResult().getChildren()) {
                                WorkoutDatabaseManager.FirebasePublicWorkoutPlan receivePlan =
                                        g.getValue(WorkoutDatabaseManager.FirebasePublicWorkoutPlan.class);
                                if (receivePlan == null) {
                                    Log.e("firebase", "Data = null", t.getException());
                                } else if (receivePlan.getPlanID().equals(planID)) {
                                    Log.e("test", receivePlan.getPlanID());
                                    Log.e("test", "public:" + receivePlan.getPlanID());
                                    workouts.add(WorkoutDatabaseManager.toPublicPlan(receivePlan, getActivity()));
                                    adapter.notifyItemInserted(workouts.size() - 1);
                                }
                            }
                        }
                    });
                }
            }
        });
        return view;
    }
}
