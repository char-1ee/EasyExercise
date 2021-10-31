package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Message;
import com.example.myapplication.beans.PublicPlan;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.databases.SportAndFacilityDBHelper;
import com.example.myapplication.databases.WorkoutDatabaseManager;
import com.example.myapplication.ui.adapters.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoomActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private final List<Message> msgList = new ArrayList<>();
    private EditText inputText;
    private TextView planFacility;
    private TextView planStartTime;
    private TextView planEndTime;
    private TextView planLimit;
    private TextView planSport;
    private Button send;
    private Button join;
    private Button quit;
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private WorkoutDatabaseManager.FirebasePublicPlan currentPlan;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        String planID;

        Bundle extras = getIntent().getExtras();
        planID = extras.getString("plan");


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://cz2006-9c928-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference mDatabase = database.getReference().child("community").child(planID).child("chatroom");
        DatabaseReference planReference = database.getReference().child("community").child(planID);
        DatabaseReference users = database.getReference().child("user");

        inputText = findViewById(R.id.inputText);
        send = findViewById(R.id.send);
        recyclerView = findViewById(R.id.messageRecyclerView);
        join = findViewById(R.id.joinPublicPlanButton);
        quit = findViewById(R.id.quitPublicPlanButton);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        planStartTime = findViewById(R.id.start_time_view);
        planEndTime = findViewById(R.id.end_time_view);
        planLimit = findViewById(R.id.planLimitView);
        planSport = findViewById(R.id.publicPlanSport);
        planFacility = findViewById(R.id.publicPlanFacility);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatRoomActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MessageAdapter(msgList);
        recyclerView.setAdapter(adapter);


        planReference.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                currentPlan = task.getResult().getValue(WorkoutDatabaseManager.FirebasePublicPlan.class);
                Date startTime = new Date(currentPlan.getPlanStart());
                Date endTime = new Date(currentPlan.getPlanFinish());
                planStartTime.setText(getTime(startTime));
                planEndTime.setText(getTime(endTime));
                SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(ChatRoomActivity.this);
                manager.openDatabase();
                Sport sport = manager.getSportById(currentPlan.getSport());
                Facility facility = manager.getFacilityById(currentPlan.getFacility());
                manager.closeDatabase();
                planLimit.setText(currentPlan.getMembers().size() + "/" + currentPlan.getPlanLimit());
                planSport.setText(sport.getName());
                planFacility.setText(facility.getName());
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


        send.setOnClickListener(v -> {

            String content = inputText.getText().toString();
            if(!"".equals(content)){
                try{

                    Message handleMsg = new Message(content, currentUser.getDisplayName(), currentUser.getPhotoUrl().toString());
                    mDatabase.child(String.valueOf(msgList.size() + 1)).setValue(handleMsg);
                    inputText.setText("");

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        join.setOnClickListener(view -> {
            planReference.get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    currentPlan = task.getResult().getValue(WorkoutDatabaseManager.FirebasePublicPlan.class);
                }
            });

            if(currentPlan.getPlanLimit() > currentPlan.getMembers().size()){
                int i;
                for (i = 0; i < currentPlan.getMembers().size(); i++){
                    if(currentUser.getUid().equals(currentPlan.getMembers().get(i))){
                        Toast.makeText(ChatRoomActivity.this, "You are already in this shared plan", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if(i == currentPlan.getMembers().size()){
                    currentPlan.addMembers(currentUser.getUid());
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("members", currentPlan.getMembers());
                    planReference.updateChildren(updates);
                    Toast.makeText(ChatRoomActivity.this, "Join successful", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(ChatRoomActivity.this, "This plan is full", Toast.LENGTH_SHORT).show();
            }
        });

        quit.setOnClickListener(view -> {
            planReference.get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    currentPlan = task.getResult().getValue(WorkoutDatabaseManager.FirebasePublicPlan.class);
                }
            });

            int i;
            boolean find = false;
            for (i = 0; i < currentPlan.getMembers().size(); i++){
                if(currentUser.getUid().equals(currentPlan.getMembers().get(i))){
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
        });
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}