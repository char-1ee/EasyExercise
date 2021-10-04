package com.example.myapplication.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Message;
import com.example.myapplication.ui.adapters.MessageAdapter;

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

        msgList.add(new Message("Hello World!", "Charles"));

        inputText = (EditText) findViewById(R.id.inputText);
        send = (Button) findViewById(R.id.send);
        recyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);

        Log.w("Chatroom", "TextLog!!!");
        if(recyclerView == null){
            Log.wtf("Chatroom", "Woc!!!");
        }
        if(recyclerView == null)
        {
            Log.wtf("Chatroom", "Noooo!!!");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatRoomActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MessageAdapter(msgList);
        recyclerView.setAdapter(adapter);


        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    try{
                        Message handleMsg = new Message(content, "Charles");
                        inputText.setText("");
                        msgList.add(handleMsg);
                        adapter.notifyItemInserted(msgList.size()-1);
                        recyclerView.scrollToPosition(msgList.size()-1);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}