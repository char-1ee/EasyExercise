package com.example.myapplication.ui.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.borax12.materialdaterangepicker.date.DatePickerController;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.PublicPlan;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.Workout;
import com.example.myapplication.beans.WorkoutPlan;
import com.example.myapplication.databases.WorkoutDatabaseManager;
import com.example.myapplication.sportsImage.SportsImage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The activity class for showing a specific plan, checking in or publishing it into the community.
 *
 * @author Ruan Donglin
 * @author Zhou Yuxuan
 * @author Li Xingjian
 */

public class ViewPlanActivity extends AppCompatActivity implements OnMapReadyCallback,
        TimePickerDialog.OnTimeSetListener {
    Integer finalLimit = 0;
    SupportMapFragment mapFragment;
//    TimePickerView pvTime2;
//    TimePickerView pvTime;
    OptionsPickerView pvOptions;
    private Handler handler;
    private Runnable runnable;
    private TextView startTime, endTime;
    private Date startDate, endDate;
    private GoogleMap mMap;
    private Sport sport;
    private TextView postalView, facilityView, sportView, addressView, limitView;
    private Workout plan;
    private Location location;
    private Button addPlanButton, checkInButton, deleteButton;
    private SportsImage sm;
    private CardView cardView;
    private List<Workout> workoutList;
    Integer[] limit = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initMap();
        initPicker();
        initButton();
    }

    private Workout getChosenPlan() {
        return (Workout) getIntent().getSerializableExtra("plan");
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Coordinates c = location.getCoordinates();
        // Add a marker in Sydney and move the camera
        LatLng cur = new LatLng(c.getLatitude(), c.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(cur)
                .title(location.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15f));
    }

    /**
     * Initialize adapter for recyclerview.
     */
    private void initView() {
        setContentView(R.layout.activity_view_plan);
        plan = getChosenPlan();
        facilityView = findViewById(R.id.location_view);
        sportView = findViewById(R.id.facility_view);
        postalView = findViewById(R.id.postal_view);
        addressView = findViewById(R.id.address_view);
        checkInButton = findViewById(R.id.check_in_button);
        addPlanButton = findViewById(R.id.add_plan_button);
        deleteButton = findViewById(R.id.delete_button);
        cardView = findViewById(R.id.timeView);
        cardView.setVisibility(View.GONE);
        startTime = findViewById(R.id.start_time);
        limitView = findViewById(R.id.limit);
        endTime = findViewById(R.id.end_time);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        location = plan.getLocation();
        sport = plan.getSport();
        sm = new SportsImage();
        if (location.getType() == Location.LocationType.FACILITY) {
            Facility f = (Facility) location;
            facilityView.setText(f.getName());
            addressView.setText(f.getAddress());
            postalView.setText(f.getPostalCode());
        } else {
            facilityView.setText(getString(R.string.customized_location));
            addressView.setText("");
            postalView.setText("");
        }
        sportView.setText(sport.getName());
    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void initButton() {
        addPlanButton.setOnClickListener(view -> {
            addPlanButton.setText(R.string.publish_plan_text);
//            pvTime2.show();
//            pvTime.show();
            Calendar now = Calendar.getInstance();
            TimePickerDialog tpd = TimePickerDialog.newInstance(
                    ViewPlanActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    false
            );
            tpd.show(getFragmentManager(), "TimePickerDialog");

            pvOptions.show();

            mapFragment.requireView().setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
            initHandler();
            handler.post(runnable);
        });
        checkInButton.setOnClickListener(view -> {
            Intent intent = new Intent(ViewPlanActivity.this, ExerciseActivity.class);
            intent.putExtra("ChosenLocation", location);
            intent.putExtra("ChosenSport", sport);
            startActivity(intent);
            finish();
        });
        deleteButton.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://cz2006-9c928-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference mDatabase = database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            mDatabase.child("WorkoutPlan").child(plan.getPlanID()).removeValue();
            Intent intent = new Intent(ViewPlanActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Initialize pickers for publishing the plan.
     */
    private void initPicker() {
//        pvTime = new TimePickerBuilder(this, (date1, v) -> startDate = date1)
//                .setType(new boolean[]{false, true, true, true, true, false})
//                .setCancelText("Cancel")
//                .setSubmitText("Confirm")
//                .setTitleSize(20)
//                .setTitleText("Starting Time")
//                .setOutSideCancelable(false)
//                .isCyclic(true)
//                .setLabel("y", "m", "d", "h", "min", "sec")
//                .isCenterLabel(false)
//                .isDialog(false)
//                .build();
//
//        pvTime2 = new TimePickerBuilder(this, (date2, v) -> endDate = date2)
//                .setType(new boolean[]{false, true, true, true, true, false})
//                .setCancelText("Cancel")
//                .setSubmitText("Confirm")
//                .setTitleSize(20)
//                .setTitleText("Ending Time")
//                .setOutSideCancelable(false)
//                .isCyclic(true)
//                .setLabel("y", "m", "d", "h", "min", "sec")
//                .isCenterLabel(false)
//                .isDialog(false)
//                .build();
        List<Integer> options1Items = new ArrayList<>(Arrays.asList(limit));
        pvOptions = new OptionsPickerBuilder(ViewPlanActivity.this, (options1, option2, options3, v) -> finalLimit = options1Items.get(options1))
                .setSubmitText("Confirm")
                .setCancelText("Cancel")
                .setTitleText("Person Number")
                .setSubCalSize(18)
                .setTitleSize(20)
                .setContentTextSize(18)
                .isCenterLabel(false)
                .setCyclic(true, false, false)
                .setSelectOptions(1, 1, 1)
                .setOutSideCancelable(false)
                .isDialog(false)
                .isRestoreItem(true)
                .build();
        pvOptions.setPicker(options1Items);
    }

    /**
     * Initialize handler for showing the previous choice for publishing plan.
     */
    private void initHandler() {
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (startDate == null) {
                    handler.postDelayed(this, 1000);
                } else if (startDate != null && endDate == null) {
                    startTime.setText(getTime(startDate));
                    handler.postDelayed(this, 1000);
                } else if (startDate != null && endDate != null && finalLimit == 0) {
                    startTime.setText(getTime(startDate));
                    endTime.setText(getTime(endDate));
                    handler.postDelayed(this, 1000);
                } else {
                    startTime.setText(getTime(startDate));
                    endTime.setText(getTime(endDate));
                    limitView.setText(String.valueOf(finalLimit));
                    showNormalDialog();
                }
// TODO: 同步问题
//                if(startDate.getTime() >= endDate.getTime()){
//                    Toast.makeText(ViewPlanActivity.this, "End time should be later than start time", Toast.LENGTH_SHORT).show();
//                    startTime = null;
//                    endDate = null;
//                }
            }
        };
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    /**
     * Initialize dialog for showing the details for publishing plan.
     */
    @SuppressLint("SetTextI18n")
    private void showNormalDialog() {
        Dialog dialog = new Dialog(ViewPlanActivity.this);
        Button confirmButton, cancelButton;
        TextView sportView, facilityView, peopleLimitView, startTimeView, endTimeView;
        ImageView imageView;
        Workout item = getChosenPlan();
        dialog.setContentView(R.layout.dialog_plan);
        confirmButton = dialog.findViewById(R.id.confirm);
        cancelButton = dialog.findViewById(R.id.cancel);
        sportView = dialog.findViewById(R.id.sport_view);
        imageView = dialog.findViewById(R.id.history_sport_image);
        facilityView = dialog.findViewById(R.id.location_view);
        peopleLimitView = dialog.findViewById(R.id.limit);
        startTimeView = dialog.findViewById(R.id.start_time_view);
        endTimeView = dialog.findViewById(R.id.end_time_view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sportView.setText(item.getSport().getName());
        if (item.getLocation().getType() == Location.LocationType.FACILITY) {
            Facility f = (Facility) item.getLocation();
            facilityView.setText(f.getName());
        } else {
            facilityView.setText(R.string.customized_location);
        }
        peopleLimitView.setText(finalLimit.toString());
        imageView.setImageResource(sm.SportsToImage(item.getSport()));
        startTimeView.setText(getTime(startDate));
        endTimeView.setText(getTime(endDate));
        dialog.show();
        confirmButton.setOnClickListener(view -> {
            Workout localPlan = getChosenPlan();
            Location location = plan.getLocation();
            int facility_id;
            if (location.getType() == Location.LocationType.FACILITY) {
                facility_id = ((Facility) location).getId();
            } else {
                facility_id = -1;
            }
            WorkoutDatabaseManager.FirebasePublicPlan publicPlan = new WorkoutDatabaseManager.FirebasePublicPlan(finalLimit, startDate, endDate, localPlan.getSport().getId(), facility_id, FirebaseAuth.getInstance().getCurrentUser().getUid());
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://cz2006-9c928-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference mDatabase = database.getReference().child("community");
            publicPlan.setPlan(plan.getPlanID());
            assert plan.getPlanID() != null;
            mDatabase.child(plan.getPlanID()).setValue(publicPlan);
            Toast.makeText(ViewPlanActivity.this, "Publish Plan Successful", Toast.LENGTH_SHORT).show();

            DatabaseReference workoutPlanDB = database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            workoutPlanDB.child("WorkoutPlan").child(plan.getPlanID()).removeValue();
            workoutPlanDB.child("PublicPlan").child(plan.getPlanID()).setValue(plan.getPlanID());
            Intent intent = new Intent(ViewPlanActivity.this, MainActivity.class);
            dialog.dismiss();
            startActivity(intent);
            finish();
        });

        cancelButton.setOnClickListener(view -> {
            dialog.dismiss();
            Intent intent = new Intent(ViewPlanActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0" + hourOfDayEnd : "" + hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0" + minuteEnd : "" + minuteEnd;
        String time1 = "Start time: " + hourString + "h" + minuteString;
        String time2 = "Start time: " + hourStringEnd + "h" + minuteStringEnd;
        startTime.setText(time1);
        endTime.setText(time2); // TODO： @rdl
    }
}