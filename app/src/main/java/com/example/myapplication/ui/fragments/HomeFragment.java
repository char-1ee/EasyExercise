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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

/**
 * The fragment class for home, including button to start tasks of checking in and making plan.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class HomeFragment extends Fragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Intent intentToCheckIn;
    Intent intentToPlan;
    Handler handler;
    Runnable runnable;
    MainActivity activity;
    double latitude;
    double longitude;
    Coordinates c;
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

    private void initVIew(){
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshment_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activity= (MainActivity) getActivity();
                setWeather(c);
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Reloading Weather Successful", Toast.LENGTH_SHORT).show();
            }
        });
        mMakePlanButton = view.findViewById(R.id.home_plan_button);
        mCheckInButton = view.findViewById(R.id.home_checkin_button);
        AddressText = view.findViewById(R.id.addressText);
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
            startActivity(intentToCheckIn);
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
                    intentToCheckIn= activity.getCheckInList();
                    setWeather(c);
                    initButton();
                }
            }
        };
    }

    private void initSwiper(){

    }

}
