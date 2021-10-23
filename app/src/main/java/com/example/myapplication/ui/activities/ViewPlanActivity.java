package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutPlan;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ViewPlanActivity extends AppCompatActivity implements OnMapReadyCallback {
    Integer finalLimit= 0;
    TimePickerView pvTime2;
    TimePickerView pvTime;
    OptionsPickerView pvOptions;
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
    private WorkoutPlan plan;
    private Location location;
    private Button addPlanButton;
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
        addPlanButton= findViewById(R.id.add_plan_button);
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
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void initButton(){
        addPlanButton.setOnClickListener(view -> {
            pvOptions.show();
            pvTime2.show();
            pvTime.show();
            initHandler();
            handler.post(runnable);
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
                .isDialog(true)
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
                .isDialog(true)
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
                .isDialog(true)
                .isRestoreItem(true)
                .build();
        pvOptions.setPicker(options1Items);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void initHandler(){
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (startDate== null || endDate== null || finalLimit== 0) {
                    Toast.makeText(ViewPlanActivity.this, "Please Give Respond", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(this, 3000);
                }
                else{ Toast.makeText(ViewPlanActivity.this, "Publish Plan Successful", Toast.LENGTH_SHORT).show();
                    TextView textView= findViewById(R.id.textView4);
                    textView.setText(finalLimit.toString());
                }
            }
        };
    }

}