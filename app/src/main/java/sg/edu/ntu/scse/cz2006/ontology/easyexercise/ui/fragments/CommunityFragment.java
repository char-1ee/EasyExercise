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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.WorkoutDatabaseManager;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.ChatRoomActivity;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters.CommunityAdapter;


public class CommunityFragment extends Fragment {
    private final List<WorkoutDatabaseManager.FirebasePublicWorkoutPlan> publicPlanList = new ArrayList<>();
    View view;
    private RecyclerView recyclerView;
    private CommunityAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_community, container, false);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance(getContext().getString(R.string.firebase_database));
        DatabaseReference mDatabase = database.getReference().child("community");

        recyclerView = view.findViewById(R.id.community_plan_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CommunityAdapter(publicPlanList, publicPlan -> {
            //on click event

            Intent intent = new Intent(getContext().getApplicationContext(), ChatRoomActivity.class);
            intent.putExtra("plan", publicPlan.getPlanID());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        mDatabase.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                for (DataSnapshot s : task.getResult().getChildren()) {
                    WorkoutDatabaseManager.FirebasePublicWorkoutPlan receivePlan = s.getValue(WorkoutDatabaseManager.FirebasePublicWorkoutPlan.class);
                    if (receivePlan == null) {
                        Log.e("firebase", "Data = null", task.getException());
                    } else {
                        publicPlanList.add(receivePlan);
                        adapter.notifyItemInserted(publicPlanList.size() - 1);
                    }
                }
            }
        });
        return view;
    }

}
