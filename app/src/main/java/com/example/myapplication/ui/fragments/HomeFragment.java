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
import com.google.android.gms.tasks.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    double latitude;
    double longitude;
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
        initVIew();
        initButton();
        getCurrentLocation();
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

    /**
     * Get user's current location as latitude and longitude
     *
     * @author Ruan Donglin
     */
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isGPSEnabled()) {
                LocationServices.getFusedLocationProviderClient(requireActivity())
                        .requestLocationUpdates(locationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                LocationServices.getFusedLocationProviderClient(requireActivity())
                                        .removeLocationUpdates(this);

                                if (locationResult != null && locationResult.getLocations().size() > 0) {
                                    int index = locationResult.getLocations().size() - 1;
                                    latitude = locationResult.getLocations().get(index).getLatitude();
                                    longitude = locationResult.getLocations().get(index).getLongitude();
                                    AddressText.setText(String.valueOf(longitude));
                                    setWeather();
                                }
                            }
                        }, Looper.getMainLooper());
            } else {
                turnOnGPS();
            }
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    /**
     * Turn on device's GPS if GPS is not enabled.
     *
     * @author Ruan Donglin
     */
    private void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(requireActivity())
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
                Toast.makeText(getContext(), "GPS is already tured on", Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(requireActivity(), 2);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //Device does not have location
                        break;
                }
            }
        });
    }

    /**
     * Check if the device's GPS is enabled.
     *
     * @return the boolean value, true indicates GPS is enabled, false vice versa
     * @author Ruan Donglin
     */
    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled;
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
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
    }

    private void initButton(){
        mMakePlanButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SelectSportActivity.class);
            // TODO: 2021/10/11 give two sport lists, one is recommended, one is not
            intent.putExtra("RecommendedSports", (Serializable) testSelectSportRecommended());
            intent.putExtra("OtherSports", (Serializable) testSelectSportOther());
            startActivity(intent);
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

    private void setWeather(){
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
        Coordinates temp= new Coordinates(latitude, longitude, "no");
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

}



