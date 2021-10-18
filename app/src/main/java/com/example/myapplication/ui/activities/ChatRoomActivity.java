package com.example.myapplication.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// TODO CANNOT RESOLVE SYMBOL
import com.example.myapplication.R;
import com.example.myapplication.beans.Message;
import com.example.myapplication.ui.adapters.MessageAdapter;
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
    private RecyclerView recyclerView;
    private MessageAdapter adapter;


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

        inputText = (EditText) findViewById(R.id.inputText);
        send = (Button) findViewById(R.id.send);
        recyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatRoomActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MessageAdapter(msgList);
        recyclerView.setAdapter(adapter);



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

    }
}