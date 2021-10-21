
package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.sportsImage.SportsImage;

import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
    private boolean timerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initButton();
        startTimer();
    }

    private Location getLocation() {
        Location l = (Location) getIntent().getSerializableExtra("ChosenLocation");
        return l;
    }

    private Sport getSport() {
        Sport s = (Sport) getIntent().getSerializableExtra("ChosenSport");
        return s;
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());
                    }
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
        sportView= (ImageView) findViewById(R.id.imageView3);
        timerText = (TextView) findViewById(R.id.timerText);
        checkOutButton = (Button) findViewById(R.id.check_out_button);
        sportView.setImageResource(sm.SportsToImage(sport));
        timer = new Timer();
        timerStarted = true;
    }

    private void initButton(){
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkInIntent = new Intent(ExerciseActivity.this, CheckOutActivity.class);
                checkInIntent.putExtra("LocationExercise", location);
                checkInIntent.putExtra("SportExercise", sport);
                checkInIntent.putExtra("StartDate", startDate);
                checkInIntent.putExtra("timeDuration", getTimerText());
                startActivity(checkInIntent);
                finish();
            }
        });
    }
}