package com.example.myapplication.ui.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.PublicPlan;
import com.example.myapplication.ui.adapters.CommunityAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        adapter = new CommunityAdapter(publicPlanList);
        recyclerView.setAdapter(adapter);



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

    /*
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    try{
                        Message handleMsg = new Message(content, "Charles");
                        mDatabase.child(String.valueOf(msgList.size()+1)).setValue(handleMsg);
                        inputText.setText("");
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    */
    }


}