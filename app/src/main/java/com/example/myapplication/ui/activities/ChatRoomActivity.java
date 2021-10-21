package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Message;
import com.example.myapplication.beans.PublicPlan;
import com.example.myapplication.ui.adapters.MessageAdapter;
import com.example.myapplication.ui.fragments.CommunityFragment;
import com.example.myapplication.ui.fragments.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Implement Firebase functionalities
// FirebaseDatabase database = FirebaseDatabase.getInstance("https://ontology-5ae5d-default-rtdb.asia-southeast1.firebasedatabase.app/");
// DatabaseReference mDatabase = database.getReference().child("community");

// PublicPlan plan = new PublicPlan(8, new Date(), new Date(), 1, 1);
// String id = mDatabase.push().getKey();
// plan.setPlan(id);
// mDatabase.child(id).setValue(plan);
// mDatabase.child(id).child("members").child("10001").setValue(10001);


public class ChatRoomActivity extends AppCompatActivity {

    private List<Message> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private Button join;
    private Button quit;
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private PublicPlan currentPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        String planID;

        Bundle extras = getIntent().getExtras();
        planID = extras.getString("plan");


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ontology-5ae5d-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference mDatabase = database.getReference().child("community").child(planID).child("chatroom");
        DatabaseReference planReference = database.getReference().child("community").child(planID);

        inputText = (EditText) findViewById(R.id.inputText);
        send = (Button) findViewById(R.id.send);
        recyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        join = (Button) findViewById(R.id.joinPublicPlanButton);
        quit = (Button) findViewById(R.id.quitPublicPlanButton);


        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatRoomActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MessageAdapter(msgList);
        recyclerView.setAdapter(adapter);

        planReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    currentPlan = task.getResult().getValue(PublicPlan.class);
                }
            }
        });


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("chatroom", "Value received!");
                msgList.clear();
                for (DataSnapshot s: snapshot.getChildren()){
                    Message receiveMessage = s.getValue(Message.class);
                    msgList.add(receiveMessage);
                }
                adapter.notifyItemInserted(msgList.size() - 1);
                recyclerView.scrollToPosition(msgList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("chatroom", "Failed to read value.", error.toException());
            }
        });


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

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            currentPlan = task.getResult().getValue(PublicPlan.class);
                        }
                    }
                });

                if(currentPlan.getPlanLimit() > currentPlan.getMembers().size()){
                    int userID = 10003;
                    int i;
                    for (i = 0; i < currentPlan.getMembers().size(); i++){
                        if(userID == currentPlan.getMembers().get(i)){
                            Toast.makeText(ChatRoomActivity.this, "You are already in this shared plan", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if(i == currentPlan.getMembers().size()){
                        currentPlan.addMembers(userID);
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("members", currentPlan.getMembers());
                        planReference.updateChildren(updates);
                        Toast.makeText(ChatRoomActivity.this, "Join successful", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ChatRoomActivity.this, "This plan is full", Toast.LENGTH_SHORT).show();
                }
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            currentPlan = task.getResult().getValue(PublicPlan.class);
                        }
                    }
                });

                int userID = 10003;
                int i;
                boolean find = false;
                for (i = 0; i < currentPlan.getMembers().size(); i++){
                    if(userID == currentPlan.getMembers().get(i)){
                        find = true;
                        currentPlan.removeMembers(i);
                        if(currentPlan.getMembers().size() == 0){
                            startActivity(new Intent(ChatRoomActivity.this, MainActivity.class));
                            planReference.removeValue();
                            Toast.makeText(ChatRoomActivity.this, "You haven't join this plan", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("members", currentPlan.getMembers());
                            planReference.updateChildren(updates);
                            Toast.makeText(ChatRoomActivity.this, "Quit Successful", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                if(!find){
                    Toast.makeText(ChatRoomActivity.this, "You haven't join this plan", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}