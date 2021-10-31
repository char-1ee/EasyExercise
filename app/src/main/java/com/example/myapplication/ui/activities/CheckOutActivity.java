package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutRecord;
import com.example.myapplication.databases.WorkoutDatabaseManager;
import com.example.myapplication.sportsImage.SportsImage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * The activity class for checking out from a workout in the checking in task.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class CheckOutActivity extends AppCompatActivity {
    private ImageView profileView, sportView;
    private Button exitButton;
    private TextView timeDuration, placeView, sportNameView;
    private Sport sport;
    private Location location;
    private SportsImage sm;
    private ActionBar actionBar;
    private Date start, end;
    private WorkoutRecord workoutRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initButton();
        // TODO: 2021/10/24  addRecord();
    }

    private Date getStart() {
        return (Date) getIntent().getSerializableExtra("StartDate");
    }

    private Date getEnd() {
        return (Date) getIntent().getSerializableExtra("EndDate");
    }

    private Location getLocation() {
        return (Location) getIntent().getSerializableExtra("LocationExercise");
    }

    private Sport getSport() {
        return (Sport) getIntent().getSerializableExtra("SportExercise");
    }

    private String getTimeDuration() {
        return (String) getIntent().getSerializableExtra("timeDuration");
    }

    private void initView(){
        setContentView(R.layout.activity_check_out);
        sm= new SportsImage();
        sport = getSport();
        location = getLocation();
        start = getStart();
        end = getEnd();
        sportNameView= findViewById(R.id.checkoutSport);
        placeView= findViewById(R.id.checkoutPlace);
        sportView=findViewById(R.id.checkoutPic);
        exitButton=findViewById(R.id.exitButton);
        profileView=findViewById(R.id.checkoutProfile);
        profileView.setClipToOutline(true);
        timeDuration = findViewById(R.id.time_duration);
        sportView.setImageResource(sm.SportsToImage(sport));
        placeView.setText(location.getName());
        timeDuration.setText(getTimeDuration());
        sportNameView.setText(sport.getName());
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        updateRecord();
    }

    private void updateRecord(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://cz2006-9c928-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference user = database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        WorkoutRecord workoutRecord;
        String postId = user.push().getKey();
        workoutRecord = new WorkoutRecord(sport, location, postId, start, end, getTimeDuration());
        WorkoutDatabaseManager.FirebaseWorkoutRecord firebaseWorkoutRecord = new WorkoutDatabaseManager.FirebaseWorkoutRecord(workoutRecord);
        user.child("WorkoutRecord").child(postId).setValue(firebaseWorkoutRecord);
    }

    private void initButton(){
        exitButton.setOnClickListener(view -> {
            Intent intent= new Intent(CheckOutActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        Intent intent= new Intent(CheckOutActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        moveTaskToBack(true);
        Intent intent= new Intent(CheckOutActivity.this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}