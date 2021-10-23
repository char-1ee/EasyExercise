package com.example.myapplication.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.PublicPlan;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutPlan;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ViewPlanActivity extends AppCompatActivity implements OnMapReadyCallback {
    Integer finalLimit= 0;
    SupportMapFragment mapFragment;
    TimePickerView pvTime2;
    TimePickerView pvTime;
    OptionsPickerView pvOptions;
    private AlertDialog.Builder normalDialog;
    private TextView startTime;
    private TextView endTime;
    private Handler handler;
    private Runnable runnable;
    private Date startDate;
    private Date endDate;
    private GoogleMap mMap;
    private Sport sport;
    private TextView postalView;
    private TextView facilityView;
    private TextView sportView;
    private TextView addressView;
    private TextView limitView;
    private WorkoutPlan plan;
    private Location location;
    private Button addPlanButton, checkInButton, deleteButton;
    private CardView cardView;
    Integer[] limit= {2,3,4,5,6,7,8,9,10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initMap();
        initPicker();
        initButton();
        //sportView.setText(getTime(startDate));

    }

    private WorkoutPlan getChosenPlan() {
        return (WorkoutPlan) getIntent().getSerializableExtra("ChosenPlan");
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
     *
     * @author Ruan Donglin
     */
    private void initView(){
        setContentView(R.layout.activity_view_plan);
        plan = getChosenPlan();
        facilityView = findViewById(R.id.location_view);
        sportView = findViewById(R.id.sport_view);
        postalView = findViewById(R.id.postal_view);
        addressView = findViewById(R.id.address_view);
        checkInButton= findViewById(R.id.check_in_button);
        addPlanButton= findViewById(R.id.add_plan_button);
        deleteButton= findViewById(R.id.delete_button);
        cardView= findViewById(R.id.timeView);
        cardView.setVisibility(View.GONE);
        startTime= findViewById(R.id.start_time);
        limitView= findViewById(R.id.limit);
        endTime= findViewById(R.id.end_time);
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

    private void initMap(){
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void initButton(){
        addPlanButton.setOnClickListener(view -> {
            addPlanButton.setText("Publish Plan");
            pvOptions.show();
            pvTime2.show();
            pvTime.show();
            mapFragment. getView().setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
            initHandler();
            handler.post(runnable);
        });
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ViewPlanActivity.this, ExerciseActivity.class);
                intent.putExtra("ChosenLocation", location);
                intent.putExtra("ChosenSport", sport);
                startActivity(intent);
                finish();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2021/10/24 delete this plan 
            }
        });
    }

    private void initPicker(){
        pvTime = new TimePickerBuilder(this, (date1, v) -> startDate= date1)
                .setType(new boolean[]{false, true, true, true, true, false})
                .setCancelText("Cancel")
                .setSubmitText("Confirm")
                .setTitleSize(20)
                .setTitleText("Starting Time")
                .setOutSideCancelable(false)
                .isCyclic(true)
                .setLabel("y","m","d","h","min","sec")
                .isCenterLabel(false)
                .isDialog(false)
                .build();

        pvTime2 = new TimePickerBuilder(this, (date2, v) -> endDate= date2)
                .setType(new boolean[]{false, true, true, true, true, false})
                .setCancelText("Cancel")
                .setSubmitText("Confirm")
                .setTitleSize(20)
                .setTitleText("Ending Time")
                .setOutSideCancelable(false)
                .isCyclic(true)
                .setLabel("y","m","d","h","min","sec")
                .isCenterLabel(false)
                .isDialog(false)
                .build();
        List<Integer> options1Items = new ArrayList<>(Arrays.asList(limit));
        pvOptions = new OptionsPickerBuilder(ViewPlanActivity.this, (options1, option2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            finalLimit= options1Items.get(options1);
        })
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


    private void initHandler(){
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if(startDate== null){
                    handler.postDelayed(this, 1000);
                }
                else if(startDate!= null && endDate== null){
                    startTime.setText(getTime(startDate));
                    handler.postDelayed(this, 1000);
                    //Toast.makeText(ViewPlanActivity.this, "Please Give Respond", Toast.LENGTH_SHORT).show();
                }
                else if(startDate!= null && endDate!= null && finalLimit== 0){
                    startTime.setText(getTime(startDate));
                    endTime.setText(getTime(endDate));
                //Toast.makeText(ViewPlanActivity.this, "Please Give Respond", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(this, 1000);
                }
                else{
                    startTime.setText(getTime(startDate));
                    endTime.setText(getTime(endDate));
                    limitView.setText(String.valueOf(finalLimit));
                    showNormalDialog();
                    //Toast.makeText(ViewPlanActivity.this, finalLimit.toString(), Toast.LENGTH_SHORT).show();
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

    private void showNormalDialog(){
        normalDialog =
                new AlertDialog.Builder(ViewPlanActivity.this);
        normalDialog.setTitle("Are you sure to publish plan?");
        normalDialog.setMessage(getTime(startDate)+"\n"+ getTime(endDate)+ "\n"+ finalLimit.toString());
        normalDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WorkoutPlan localPlan = getChosenPlan();
                        Location location = plan.getLocation();
                        int facility_id;
                        if (location.getType() == Location.LocationType.FACILITY) {
                            facility_id = ((Facility) location).getId();
                        } else {
                            facility_id = -1;
                        }
                        PublicPlan publicPlan = new PublicPlan(finalLimit, startDate, endDate, localPlan.getSport().getId(), facility_id);
                        publicPlan.addMembers(10001);
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ontology-5ae5d-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference mDatabase = database.getReference().child("community");
                        String postId = mDatabase.push().getKey();
                        mDatabase.child(postId).setValue(publicPlan);
                        Toast.makeText(ViewPlanActivity.this, "Publish Plan Successful", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(ViewPlanActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        normalDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent= new Intent(ViewPlanActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        normalDialog.show();
    }


}