package com.example.myapplication.ui.fragments;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

public class Dialog extends android.app.Dialog {
    private TextView sportView, facilityView, durationView, startTimeView, endTimeView;

    public Dialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_history);
        sportView = findViewById(R.id.planLimitView);
        facilityView = findViewById(R.id.facility_view);
        durationView = findViewById(R.id.duration_view);
        startTimeView = findViewById(R.id.end_time_view);
        endTimeView = findViewById(R.id.start_time_view);
        ;
    }
}
