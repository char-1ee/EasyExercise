package com.example.myapplication.ui.activities;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private List<Message> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private Button join;
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
                for (DataSnapshot s : snapshot.getChildren()) {
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


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    try {
                        Message handleMsg = new Message(content, "Charles");
                        mDatabase.child(String.valueOf(msgList.size() + 1)).setValue(handleMsg);
                        inputText.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPlan.getPlanLimit() > currentPlan.getCurrentMember()){
                    planReference.child("members").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {
                                int userID = 10002;
                                boolean find = false;
                                for (DataSnapshot s: task.getResult().getChildren()){
                                    if(userID == s.getValue(Integer.class)){
                                        Toast.makeText(ChatRoomActivity.this, "You are already in this shared plan.", Toast.LENGTH_SHORT).show();
                                        find = true;
                                        break;
                                    }
                                }
                                if(!find){
                                    planReference.child("members").child(Integer.toString(userID)).setValue(userID);
                                    currentPlan.setCurrentMember(currentPlan.getCurrentMember()+1);
                                    planReference.child("currentMember").setValue(currentPlan.getCurrentMember());
                                }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(ChatRoomActivity.this, "This plan is full.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}