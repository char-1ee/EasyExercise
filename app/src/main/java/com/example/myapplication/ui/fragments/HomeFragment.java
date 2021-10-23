package com.example.myapplication.ui.fragments;

import static com.example.myapplication.json.weather.Weather.AIR_TEMPERATURE_JSON_URL;
import static com.example.myapplication.json.weather.Weather.PM25_JSON_URL;
import static com.example.myapplication.json.weather.Weather.RAINFALL_JSON_URL;
import static com.example.myapplication.json.weather.Weather.RELATIVE_HUMIDITY_JSON_URL;
import static com.example.myapplication.json.weather.Weather.UV_INDEX_JSON_URL;
import static com.example.myapplication.json.weather.Weather.WEATHER_FORECAST_JSON_URL;
import static com.example.myapplication.json.weather.Weather.WIND_DIRECTION_JSON_URL;
import static com.example.myapplication.json.weather.Weather.WIND_SPEED_JSON_URL;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.json.weather.Weather;
import com.example.myapplication.ui.activities.CheckInNormalActivity;
import com.example.myapplication.ui.activities.MainActivity;
import com.example.myapplication.ui.activities.NoFacilityActivity;
import com.example.myapplication.ui.activities.SelectSportActivity;
import com.example.myapplication.ui.activities.ViewPlanActivity;
import com.example.myapplication.utils.Box;
import com.example.myapplication.utils.IOUtil;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    Intent intentToPlan;
    Handler handler;
    Runnable runnable;
    MainActivity activity;
    double latitude;
    double longitude;
    Coordinates c;
    private LocationRequest locationRequest;
//    private AddPlanAdapter firstAdapter;
//    public Facility closestFacility;
//    public List<Facility> FacilityByDistance;
    private TextView AddressText;
    View view;
    Button mMakePlanButton;
    Button mCheckInButton;
    TextView temperature, pm25, uvIndex, humidity, forecast;
    String temperature_string, pm25_string, uvIndex_string, humidity_string, forecast_string;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        latitude= 0;
        longitude= 0;
        initVIew();
        return view;
    }


    private Facility testCheckinClosetFacility() {
        Sport a = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Facility r = new Facility(0, "wave", "http://www.ringoeater.com/", "84073568", "64 Nanyang Cres", "nonononono", new Coordinates(0, 0));
        r.addSport(a);
        r.addSport(b);
        r.addSport(c);
        return r;
    }

    private List<Facility> testCheckinFacilitByDistance(){
        Sport a= new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b= new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c= new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Facility r= new Facility( 0, "wave", "http://www.ringoeater.com/", "84073568","64 Nanyang Cres","nonononono",new Coordinates(0, 0));
        r.addSport(a);
        r.addSport(b);
        r.addSport(c);
        List<Facility> f = new ArrayList<>();
        f.add(testCheckinClosetFacility());
        f.add(r);
        f.add(testCheckinClosetFacility());
        f.add(r);
        f.add(testCheckinClosetFacility());
        f.add(r);
        return f;
    }

    private List<Sport> testSelectSportRecommended(){
        List<Sport> sports= new ArrayList<>();
        Sport a= new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b= new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c= new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        sports.add(a);
        sports.add(b);
        sports.add(c);
        return sports;
    }

    private List<Sport> testSelectSportOther() {
        List<Sport> sports = new ArrayList<>();
        Sport a = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c = new Sport(0, "Swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        sports.add(a);
        sports.add(b);
        sports.add(c);
        return sports;
    }

    private void initVIew(){
        mMakePlanButton = view.findViewById(R.id.home_plan_button);
        mCheckInButton = view.findViewById(R.id.home_checkin_button);
        AddressText = view.findViewById(R.id.addressText);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        temperature = view.findViewById(R.id.temperature);
        pm25 = view.findViewById(R.id.pm25_value);
        uvIndex = view.findViewById(R.id.UV_value);
        humidity = view.findViewById(R.id.Humidity_value);
        forecast = view.findViewById(R.id.Forecast);
        activity= (MainActivity) getActivity();
        longitude= activity.getLongitude();
        latitude= activity.getLatitude();
        initHandler();
        handler.post(runnable);
    }

    private void initButton(){
        mMakePlanButton.setOnClickListener(v -> {
            startActivity(intentToPlan);
        });

        mCheckInButton.setOnClickListener(v -> {
            // TODO: 2021/10/11 give the closestfacility(one) and a list of facilities sorted by distance
            Intent intent;
            if(facilityAround()){
                intent = new Intent(getActivity(), CheckInNormalActivity.class);
                intent.putExtra("ClosestFacility", testCheckinClosetFacility());
                intent.putExtra("FacilityByDistance", (Serializable) testCheckinFacilitByDistance());
            } else {
                intent = new Intent(getActivity(), NoFacilityActivity.class);
            }
            startActivity(intent);
        });
    }

    private void setWeather(Coordinates temp){
        final Box<Weather> boxWeather = new Box<>();
        Thread thread = new Thread(() -> boxWeather.set(new Weather(
                IOUtil.readFromURL(AIR_TEMPERATURE_JSON_URL),
                IOUtil.readFromURL(RAINFALL_JSON_URL),
                IOUtil.readFromURL(RELATIVE_HUMIDITY_JSON_URL),
                IOUtil.readFromURL(WIND_DIRECTION_JSON_URL),
                IOUtil.readFromURL(WIND_SPEED_JSON_URL),
                IOUtil.readFromURL(UV_INDEX_JSON_URL),
                IOUtil.readFromURL(PM25_JSON_URL),
                IOUtil.readFromURL(WEATHER_FORECAST_JSON_URL))));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ignored) {
        }
        Weather weather = boxWeather.get();
        temperature_string = weather.getWeatherData(temp).getTemperature().getResult().toString();
        pm25_string = weather.getWeatherData(temp).getPM25().getResult().toString();
        uvIndex_string = weather.getWeatherData(temp).getUVIndex().toString();
        humidity_string = weather.getWeatherData(temp).getRelativeHumidity().getResult().toString();
        forecast_string = weather.getWeatherData(temp).getForecast().getResult();
        temperature.setText(temperature_string);
        pm25.setText(pm25_string);
        uvIndex.setText(uvIndex_string);
        humidity.setText(humidity_string);
        forecast.setText(forecast_string);
    }

    private boolean facilityAround(){
        return true;
    }

    private void initHandler(){
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (latitude== 0) {
                    latitude= activity.getLatitude();
                    handler.postDelayed(this, 2000);
                }
                else{
                    AddressText.setText(String.valueOf(latitude));
                    c= new Coordinates(latitude, longitude, "new");
                    intentToPlan= activity.getIntentToPlan();
                    setWeather(c);
                    initButton();
                }
            }
        };
    }



}



