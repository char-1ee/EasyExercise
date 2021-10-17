
package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;

import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ExerciseActivity extends AppCompatActivity {
    TextView timerText;
    Button stopStartButton;
    Button checkOutButton;

    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    boolean timerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        timerText = (TextView) findViewById(R.id.timerText);
        stopStartButton = (Button) findViewById(R.id.start_stop_button);
        checkOutButton = (Button) findViewById(R.id.check_out_button);

        timer = new Timer();
        timerStarted = true;
        setButtonUI("STOP", R.color.purple_200);

        startTimer();

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent checkInIntent = new Intent(ExerciseActivity.this, CheckOutActivity.class);
                checkInIntent.putExtra("timeDuration", getTimerText());
                startActivity(checkInIntent);
            }
        });
    }


    public void startStopTapped(View view) {
        if (timerStarted == false) {
            timerStarted = true;
            setButtonUI("STOP", R.color.purple_200);
            startTimer();
        } else {
            timerStarted = false;
            setButtonUI("START", R.color.purple_700);
            timerTask.cancel();
        }
    }

    private void setButtonUI(String start, int color) {
        stopStartButton.setText(start);
        stopStartButton.setTextColor(ContextCompat.getColor(this, color));
    }

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


}
