package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.fragments;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;

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
