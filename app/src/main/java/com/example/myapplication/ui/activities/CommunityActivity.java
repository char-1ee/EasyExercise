package com.example.myapplication.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.PublicPlan;
import com.example.myapplication.ui.adapters.CommunityAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {

    private List<PublicPlan> publicPlanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommunityAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ontology-5ae5d-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference mDatabase = database.getReference().child("community");

        recyclerView = (RecyclerView) findViewById(R.id.community_plan_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(CommunityActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CommunityAdapter(publicPlanList, publicPlan -> {
            //on click event
            Toast.makeText(CommunityActivity.this, "123", Toast.LENGTH_LONG).show();

            //startActivity(new Intent(CommunityActivity.this, ChatRoomActivity.class));
        });
        recyclerView.setAdapter(adapter);

        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(CommunityActivity.this, "fail", Toast.LENGTH_LONG).show();
                }
                else {
                    for (DataSnapshot s: task.getResult().getChildren()){
                        PublicPlan receivePlan = s.getValue(PublicPlan.class);
                        publicPlanList.add(receivePlan);
                        adapter.notifyItemInserted(publicPlanList.size() - 1);
                        Toast.makeText(CommunityActivity.this, receivePlan.getPlanStart(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        /*
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                publicPlanList.clear();
                for (DataSnapshot s: snapshot.getChildren()){
                    PublicPlan receivePlan = s.getValue(PublicPlan.class);
                    publicPlanList.add(receivePlan);
                }
                adapter.notifyItemInserted(publicPlanList.size() - 1);
                recyclerView.scrollToPosition(publicPlanList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("chatroom", "Failed to read value.", error.toException());
            }
        });
        */

    }
}