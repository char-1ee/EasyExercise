
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
import com.example.myapplication.sportsImage.SportsImage;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The activity class for staying in an exercise in the checking in task.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class ExerciseActivity extends AppCompatActivity {
    private Date startDate;
    private Location location;
    private Sport sport;
    private TextView timerText;
    private Button checkOutButton;
    private ImageView sportView;
    private Timer timer;
    private TimerTask timerTask;
    private Double time = 0.0;
    private SportsImage sm;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initButton();
        startTimer();
    }

    private Location getLocation() {
        return (Location) getIntent().getSerializableExtra("ChosenLocation");
    }

    private Sport getSport() {
        return (Sport) getIntent().getSerializableExtra("ChosenSport");
    }

    /**
     * Start timer and change the text for timer accordingly.
     *
     * @author Ruan Donglin
     */
    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    time++;
                    timerText.setText(getTimerText());
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private String getTimerText() {
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);
        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + " : " +
                String.format("%02d", minutes) + " : " +
                String.format("%02d", seconds);
    }

    private void initView(){
        setContentView(R.layout.activity_exercise);
        startDate = new Date();
        sm= new SportsImage();
        location = getLocation();
        sport = getSport();
        sportView= findViewById(R.id.imageView3);
        timerText = findViewById(R.id.timerText);
        checkOutButton = findViewById(R.id.check_out_button);
        sportView.setImageResource(sm.SportsToImage(sport));
        timer = new Timer();
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initButton(){
        checkOutButton.setOnClickListener(view -> {
            Intent checkInIntent = new Intent(ExerciseActivity.this, CheckOutActivity.class);
            checkInIntent.putExtra("LocationExercise", location);
            checkInIntent.putExtra("SportExercise", sport);
            checkInIntent.putExtra("StartDate", startDate);
            checkInIntent.putExtra("timeDuration", getTimerText());
            checkInIntent.putExtra("EndDate", new Date());
            startActivity(checkInIntent);
            finish();
        });
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        Intent intent= new Intent(ExerciseActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        moveTaskToBack(true);
        Intent intent= new Intent(ExerciseActivity.this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}