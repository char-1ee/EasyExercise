package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Location;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Workout;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.WorkoutDatabaseManager;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.SportsImageMatcher;

/**
 * The activity class for showing a specific plan, checking in or publishing it into the community.
 *
 * @author Ruan Donglin
 * @author Zhou Yuxuan
 * @author Li Xingjian
 * @author Zhong Ruoyu
 */
public class ViewPlanActivity extends AppCompatActivity implements OnMapReadyCallback, TimePickerDialog.OnTimeSetListener {
    private int finalLimit = 0;
    Calendar start;
    Calendar end;
    private TimePickerDialog tpd;
    private DatePickerDialog picker;
    private SupportMapFragment mapFragment;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;
    private OptionsPickerView pvOptions;
    private Handler handler;
    private Handler handler2;
    private Handler handler3;
    private Runnable runnable;
    private Runnable runnable2;
    private Runnable runnable3;
    private TextView startTime;
    private TextView endTime;
    private Sport sport;
    private TextView limitView;
    private Workout plan;
    private Location location;
    private Button buttonPublishPlan;
    private Button buttonCheckIn;
    private Button deleteButton;
    private CardView cardView;

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
        Coordinates c = location.getCoordinates();
        // Add a marker in Sydney and move the camera
        LatLng cur = new LatLng(c.getLatitude(), c.getLongitude());
        googleMap.addMarker(new MarkerOptions()
                .position(cur)
                .title(location.getName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15f));
    }

    /**
     * Initialize adapter for recyclerview.
     */
    private void initView() {
        setContentView(R.layout.activity_view_plan);
        plan = getChosenPlan();
        TextView facilityView = findViewById(R.id.location_view);
        TextView sportView = findViewById(R.id.facility_view);
        TextView postalView = findViewById(R.id.postal_view);
        TextView addressView = findViewById(R.id.address_view);
        buttonCheckIn = findViewById(R.id.check_in_button);
        buttonPublishPlan = findViewById(R.id.publish_plan_button);
        deleteButton = findViewById(R.id.delete_button);
        cardView = findViewById(R.id.timeView);
        cardView.setVisibility(View.GONE);
        startTime = findViewById(R.id.start_time);
        limitView = findViewById(R.id.limit);
        endTime = findViewById(R.id.end_time);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        location = plan.getLocation();
        sport = plan.getSport();
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
        buttonPublishPlan.setOnClickListener(view -> {
            buttonPublishPlan.setText(R.string.publish_plan_text);
            year = 0;
            finalLimit = 0;
            Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            picker = new DatePickerDialog(ViewPlanActivity.this,
                    (v, y, m, d) -> {
                        ViewPlanActivity.this.year = y;
                        ViewPlanActivity.this.monthOfYear = m;
                        ViewPlanActivity.this.dayOfMonth = d;
                    }, year, month, day);
            picker.show();
            initHandler3();
            handler3.post(runnable3);
            Calendar now = Calendar.getInstance();
            tpd = TimePickerDialog.newInstance(
                    ViewPlanActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    false
            );
            initHandler2();
            handler2.post(runnable2);
            initHandler();
            handler.post(runnable);
        });
        buttonCheckIn.setOnClickListener(view -> {
            Intent intent = new Intent(ViewPlanActivity.this, ExerciseActivity.class);
            intent.putExtra("ChosenLocation", location);
            intent.putExtra("ChosenSport", sport);
            startActivity(intent);
            finish();
        });
        deleteButton.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance(getString(R.string.firebase_database));
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
        List<Integer> options1Items = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        pvOptions = new OptionsPickerBuilder(ViewPlanActivity.this, (options1, option2, options3, v) -> finalLimit = options1Items.get(options1))
                .setSubmitText("Confirm")
                .setCancelText("Cancel")
                .setTitleText("Capacity")
                .setSubCalSize(18)
                .setTitleSize(20)
                .setContentTextSize(18)
                .isCenterLabel(false)
                .setCyclic(true, false, false)
                .setSelectOptions(1, 1, 1)
                .setOutSideCancelable(false)
                .isDialog(true)
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
                if (finalLimit == 0) {
                    handler.postDelayed(this, 500);
                } else {
                    limitView.setText(String.valueOf(finalLimit));
                    showNormalDialog();
                    mapFragment.requireView().setVisibility(View.GONE);
                    cardView.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    private void initHandler2() {
        handler2 = new Handler();
        runnable2 = new Runnable() {
            public void run() {
                if (start != null) {
                    pvOptions.show();
                } else {
                    handler2.postDelayed(this, 500);
                }
            }
        };
    }

    private void initHandler3() {
        handler3 = new Handler();
        runnable3 = new Runnable() {
            public void run() {
                if (year != 0) {
                    tpd.show(getFragmentManager(), "TimePickerDialog");
                } else {
                    handler3.postDelayed(this, 500);
                }
            }
        };
    }

    private String getTime(Date date) {
        @SuppressLint("SimpleDateFormat")
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
        peopleLimitView.setText(String.valueOf(finalLimit));
        imageView.setImageResource(SportsImageMatcher.getImage(item.getSport()));
        startTimeView.setText(getTime(start.getTime()));
        endTimeView.setText(getTime(end.getTime()));
        dialog.show();
        confirmButton.setOnClickListener(view -> {
            Workout localPlan = getChosenPlan();
            Location location = plan.getLocation();
            long facilityId;
            if (location.getType() == Location.LocationType.FACILITY) {
                facilityId = ((Facility) location).getId();
            } else {
                facilityId = -1;
            }
            WorkoutDatabaseManager.FirebasePublicWorkoutPlan publicPlan =
                    new WorkoutDatabaseManager.FirebasePublicWorkoutPlan(
                            finalLimit,
                            start.getTime(),
                            end.getTime(),
                            plan.getPlanID(),
                            localPlan.getSport().getId(),
                            facilityId,
                            FirebaseAuth.getInstance().getCurrentUser().getUid());
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database));
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("community");
            assert plan.getPlanID() != null;
            databaseReference.child(plan.getPlanID()).setValue(publicPlan);
            Toast.makeText(
                    ViewPlanActivity.this,
                    "Plan published successfully.",
                    Toast.LENGTH_SHORT
            ).show();

            DatabaseReference workoutPlanDB =
                    firebaseDatabase.getReference()
                            .child("user")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        start = new Calendar.Builder()
                .setDate(year, monthOfYear, dayOfMonth)
                .setTimeOfDay(hourOfDay, minute, 0).build();
        end = new Calendar.Builder()
                .setDate(year, monthOfYear, dayOfMonth)
                .setTimeOfDay(hourOfDayEnd, minuteEnd, 0).build();
        startTime.setText(getTime(start.getTime()));
        endTime.setText(getTime(end.getTime()));
    }
}
