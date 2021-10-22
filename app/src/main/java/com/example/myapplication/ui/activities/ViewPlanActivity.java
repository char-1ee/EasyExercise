package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
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
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ViewPlanActivity extends AppCompatActivity implements OnMapReadyCallback {
    TimePickerView pvTime2;
    TimePickerView pvTime;
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
    private Button button;

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
        addPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTime2.show();
                pvTime.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        TextView textView= findViewById(R.id.textView4);
                        textView.setText(getTime(endDate));
                    }
                }, 5000);
            }
        });
    }

    private void initPicker(){
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date1, View v) {
                //Toast.makeText(ViewPlanActivity.this, getTime(date1), Toast.LENGTH_SHORT).show();
                startDate= date1;
            }
        })
                .setType(new boolean[]{false, true, true, true, true, false})// 默认全部显示
                .setCancelText("Cancel")
                .setSubmitText("Confirm")
                .setTitleSize(20)//标题文字大小
                .setTitleText("Select Starting Time")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setLabel("y","m","d","h","min","sec")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();

        pvTime2 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2,View v) {
                endDate= date2;
                //Toast.makeText(ViewPlanActivity.this, getTime(date2), Toast.LENGTH_SHORT).show();
            }
        })
                .setType(new boolean[]{false, true, true, true, true, false})// 默认全部显示
                .setCancelText("Cancel")
                .setSubmitText("Confirm")
                .setTitleSize(20)//标题文字大小
                .setTitleText("Select Ending Time")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setLabel("y","m","d","h","min","sec")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

}