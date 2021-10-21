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
import android.os.Build;
import android.os.Bundle;
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
import com.example.myapplication.ui.activities.NoFacilityActivity;
import com.example.myapplication.ui.activities.SelectSportActivity;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    double latitude;
    double longitude;
    Coordinates c;
    public Facility closestFacility;
    public List<Facility> FacilityByDistance;
    private TextView AddressText;
    View view;
    Button mMakePlanButton;
    Button mCheckInButton;
    TextView temperature, pm25, uvIndex, humidity, forecast;
    String temperature_string, pm25_string, uvIndex_string, humidity_string, forecast_string;
    Coordinates temp;
    private LocationRequest locationRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mMakePlanButton = view.findViewById(R.id.home_plan_button);
        mCheckInButton = view.findViewById(R.id.home_checkin_button);
        AddressText = view.findViewById(R.id.addressText);
        mMakePlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectSportActivity.class);
                // TODO: 2021/10/11 give two sport lists, one is recommended, one is not
                intent.putExtra("RecommendedSports", (Serializable) testSelectSportRecommended());
                intent.putExtra("OtherSports", (Serializable) testSelectSportOther());
                startActivity(intent);
            }
        });


        mCheckInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2021/10/11 give the closestfacility(one) and a list of facilities sorted by distance
                if( 1==1){
                    Intent intent = new Intent(getActivity(), CheckInNormalActivity.class);
                    intent.putExtra("ClosestFacility", testCheckinClosetFacility());
                    intent.putExtra("FacilityByDistance", (Serializable) testCheckinFacilitByDistance());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), NoFacilityActivity.class);
                    startActivity(intent);
                }
            }
        });

        temperature = view.findViewById(R.id.temperature);
        pm25 = view.findViewById(R.id.pm25_value);
        uvIndex = view.findViewById(R.id.UV_value);
        humidity = view.findViewById(R.id.Humidity_value);
        forecast = view.findViewById(R.id.Forecast);

        final Box<Weather> boxWeather = new Box<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boxWeather.set(new Weather(
                        IOUtil.readFromURL(AIR_TEMPERATURE_JSON_URL),
                        IOUtil.readFromURL(RAINFALL_JSON_URL),
                        IOUtil.readFromURL(RELATIVE_HUMIDITY_JSON_URL),
                        IOUtil.readFromURL(WIND_DIRECTION_JSON_URL),
                        IOUtil.readFromURL(WIND_SPEED_JSON_URL),
                        IOUtil.readFromURL(UV_INDEX_JSON_URL),
                        IOUtil.readFromURL(PM25_JSON_URL),
                        IOUtil.readFromURL(WEATHER_FORECAST_JSON_URL)));
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            ;
        }
        Weather weather = boxWeather.get();
//        temp= getLocation();
        Coordinates temp= new Coordinates(latitude, longitude, "nono");
        AddressText.setText(String.valueOf(temp.getLatitude()) + String.valueOf(temp.getLongitude()));
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
        return view;
    }


    private Facility testCheckinClosetFacility() {
        Sport a = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Facility r = new Facility(0, "wave", "http://www.ringoeater.com/", "84073568", "64 Nanyang Cres", "nonononono", new Coordinates(0, 0));
        r.addSport(a);
        r.addSport(b);
        r.addSport(c);
        return r;
    }

    private List<Facility> testCheckinFacilitByDistance(){
        Sport a= new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b= new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c= new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Facility r= new Facility( 0, "wave", "http://www.ringoeater.com/", "84073568","64 Nanyang Cres","nonononono",new Coordinates(0, 0));
        r.addSport(a);
        r.addSport(b);
        r.addSport(c);
        List<Facility> f = new ArrayList<Facility>();
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
        Sport a= new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b= new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c= new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        sports.add(a);
        sports.add(b);
        sports.add(c);
        return sports;
    }

    private List<Sport> testSelectSportOther() {
        List<Sport> sports = new ArrayList<>();
        Sport a = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport b = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        Sport c = new Sport(0, "swimming", "swimming", Sport.SportType.INDOOR_OUTDOOR);
        sports.add(a);
        sports.add(b);
        sports.add(c);
        return sports;
    }

//    private Coordinates getLocation(){
//        latitude= (double) getActivity().getIntent().getSerializableExtra("Latitude");
//        longitude= (double) getActivity().getIntent().getSerializableExtra("Longitude");
//        return new Coordinates(latitude, longitude, "nono");
//    }

}
