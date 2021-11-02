package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.fragments;

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
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.WorkoutRecord;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.WorkoutDatabaseManager;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters.HistoryRecyclerViewAdapter;

/**
 * The fragment class for showing all existing history.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 * @author Li Xingjian
 */

public class HistoryFragment extends Fragment {
    private final List<WorkoutRecord> workoutRecordList = new ArrayList<>();
    View view;
    private RecyclerView mRecyclerView;
    private HistoryRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        FirebaseDatabase database = FirebaseDatabase.getInstance(getContext().getString(R.string.firebase_database));
        DatabaseReference user = database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new HistoryRecyclerViewAdapter(workoutRecordList, getActivity());
        mRecyclerView.setAdapter(adapter);

        user.child("WorkoutRecord").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                for (DataSnapshot s : task.getResult().getChildren()) {
                    WorkoutDatabaseManager.FirebaseWorkoutRecord receiveRecord = s.getValue(WorkoutDatabaseManager.FirebaseWorkoutRecord.class);
                    if (receiveRecord == null) {
                        Log.e("firebase", "Data = null", task.getException());
                    } else {
                        Log.e("test", receiveRecord.getPlanID());
                        workoutRecordList.add(WorkoutDatabaseManager.toWorkoutRecord(receiveRecord, getActivity()));
                        Log.e("test", workoutRecordList.get(0).getPlanID());
                        adapter.notifyItemInserted(workoutRecordList.size() - 1);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
