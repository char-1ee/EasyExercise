package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

import android.annotation.SuppressLint;
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

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Message;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.SportAndFacilityDBHelper;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.WorkoutDatabaseManager;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters.MessageAdapter;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.ButtonClickUtil;

public class ChatRoomActivity extends AppCompatActivity {
    private final List<Message> msgList = new ArrayList<>();
    private EditText inputText;
    private TextView planFacility;
    private TextView planStartTime;
    private TextView planEndTime;
    private TextView planLimit;
    private TextView planSport;
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private WorkoutDatabaseManager.FirebasePublicWorkoutPlan currentPlan;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        String planID;

        Bundle extras = getIntent().getExtras();
        planID = extras.getString("plan");

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance(getString(R.string.firebase_database));
        DatabaseReference mDatabase = database.getReference().child("community").child(planID).child("chatroom");
        DatabaseReference planReference = database.getReference().child("community").child(planID);
        DatabaseReference user =
                database.getReference()
                        .child("user")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        inputText = findViewById(R.id.inputText);
        Button send = findViewById(R.id.send);
        recyclerView = findViewById(R.id.messageRecyclerView);
        Button join = findViewById(R.id.joinPublicPlanButton);
        Button quit = findViewById(R.id.quitPublicPlanButton);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
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
            } else {
                currentPlan = task.getResult().getValue(WorkoutDatabaseManager.FirebasePublicWorkoutPlan.class);
                Date startTime = new Date(currentPlan.getPlanStart());
                Date endTime = new Date(currentPlan.getPlanFinish());
                planStartTime.setText(getTime(startTime));
                planEndTime.setText(getTime(endTime));
                SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(ChatRoomActivity.this);
                manager.openDatabase();
                Sport sport = manager.getSportById(currentPlan.getSportID());
                Facility facility = manager.getFacilityById(currentPlan.getFacilityID());
                manager.closeDatabase();
                planLimit.setText(String.format("%s / %s", currentPlan.getMembers().size(), currentPlan.getPlanLimit()));
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
            if (!content.isEmpty()) {
                try {
                    Message handleMsg = new Message(content, currentUser.getDisplayName(), currentUser.getPhotoUrl().toString());
                    mDatabase.child(String.valueOf(msgList.size() + 1)).setValue(handleMsg);
                    inputText.setText("");
                } catch (Exception ignored) {
                }
            }
        });

        join.setOnClickListener(view -> {
            if (!ButtonClickUtil.isFastDoubleClick(R.id.joinPublicPlanButton)) {
                planReference.get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        currentPlan = task.getResult().getValue(WorkoutDatabaseManager.FirebasePublicWorkoutPlan.class);
                    }
                });

                if (currentPlan.getPlanLimit() > currentPlan.getMembers().size()) {
                    int i;
                    for (i = 0; i < currentPlan.getMembers().size(); i++) {
                        if (currentUser.getUid().equals(currentPlan.getMembers().get(i))) {
                            Toast.makeText(ChatRoomActivity.this, "You are already in this shared plan.", Toast.LENGTH_SHORT).show();
                            Log.wtf("test", "You are already in this shared plan.");
                            break;
                        }
                    }
                    if (i == currentPlan.getMembers().size()) {
                        currentPlan.addMember(currentUser.getUid());
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("members", currentPlan.getMembers());
                        user.child("PublicPlan").child(currentPlan.getPlanID()).setValue(currentPlan.getPlanID());
                        planReference.updateChildren(updates);
                        user.child("PublicPlan").child(currentPlan.getPlanID()).setValue(currentPlan.getPlanID());
                        planLimit.setText(String.format("%s / %s", currentPlan.getMembers().size(), currentPlan.getPlanLimit()));
                        Toast.makeText(
                                ChatRoomActivity.this,
                                "Successfully joined.",
                                Toast.LENGTH_SHORT
                        ).show();
                        Log.wtf("test", "Successfully joined.");
                    }
                } else {
                    Toast.makeText(
                            ChatRoomActivity.this,
                            "This plan is full.",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.wtf("test", "This plan is full.");
                }
            }
        });

        quit.setOnClickListener(view -> {
            if (!ButtonClickUtil.isFastDoubleClick(R.id.quitPublicPlanButton)) {
                planReference.get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data.", task.getException());
                    } else {
                        currentPlan = task.getResult().getValue(WorkoutDatabaseManager.FirebasePublicWorkoutPlan.class);
                    }
                });

                int i;
                boolean find = false;
                for (i = 0; i < currentPlan.getMembers().size(); i++) {
                    if (currentUser.getUid().equals(currentPlan.getMembers().get(i))) {
                        find = true;
                        currentPlan.removeMember(i);
                        user.child("PublicPlan").child(currentPlan.getPlanID()).removeValue();
                        if (currentPlan.getMembers().size() == 0) {
                            planReference.removeValue();
                            Toast.makeText(
                                    ChatRoomActivity.this,
                                    "You haven't joined this plan.",
                                    Toast.LENGTH_SHORT
                            ).show();
                            // TODO: BUG!!!!!!
                            startActivity(new Intent(ChatRoomActivity.this, MainActivity.class));
                        } else {
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("members", currentPlan.getMembers());
                            planReference.updateChildren(updates);
                            planLimit.setText(String.format("%s / %s", currentPlan.getMembers().size(), currentPlan.getPlanLimit()));
                            Toast.makeText(
                                    ChatRoomActivity.this,
                                    "Successfully quit.",
                                    Toast.LENGTH_SHORT
                            ).show();
                            break;
                        }
                    }
                }
                if (!find) {
                    Toast.makeText(
                            ChatRoomActivity.this,
                            "You haven't joined this plan.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private String getTime(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}