package com.example.myapplication.ui.activities;

import static com.example.myapplication.json.weather.Weather.AIR_TEMPERATURE_JSON_URL;
import static com.example.myapplication.json.weather.Weather.PM25_JSON_URL;
import static com.example.myapplication.json.weather.Weather.RAINFALL_JSON_URL;
import static com.example.myapplication.json.weather.Weather.RELATIVE_HUMIDITY_JSON_URL;
import static com.example.myapplication.json.weather.Weather.UV_INDEX_JSON_URL;
import static com.example.myapplication.json.weather.Weather.WEATHER_FORECAST_JSON_URL;
import static com.example.myapplication.json.weather.Weather.WIND_DIRECTION_JSON_URL;
import static com.example.myapplication.json.weather.Weather.WIND_SPEED_JSON_URL;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.databases.SportAndFacilityDBHelper;
import com.example.myapplication.json.weather.Weather;
import com.example.myapplication.recommendation.FacilityRecommendation;
import com.example.myapplication.recommendation.SportsRecommendation;
import com.example.myapplication.ui.fragments.CommunityFragment;
import com.example.myapplication.ui.fragments.HistoryFragment;
import com.example.myapplication.ui.fragments.HomeFragment;
import com.example.myapplication.ui.fragments.MeFragment;
import com.example.myapplication.ui.fragments.PlanFragment;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Intent intentToCheckIn;
    Intent intentToPlan;
    private LocationRequest locationRequest;
    double latitude;
    double longitude;
    List<Sport> allSports = new ArrayList<>();
    Coordinates c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        setAllSports();
        getCurrentLocation();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                if (latitude== 0) {
                    Toast.makeText(MainActivity.this, "GPS loading", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(this, 3000);
                }
                else{
                    Toast.makeText(MainActivity.this, "GPS now ready", Toast.LENGTH_SHORT).show();
                    intentToPlan = new Intent(MainActivity.this, SelectSportActivity.class);
                    intentToPlan.putExtra("RecommendedSports", (Serializable) testSelectSportRecommended());
                    intentToPlan.putExtra("OtherSports", (Serializable) testSelectSportOther());
                    //Toast.makeText(MainActivity.this, String.valueOf(latitude), Toast.LENGTH_SHORT).show();
                    intentToPlan.putExtra("latitude1", latitude);
                    intentToPlan.putExtra("longitude1", longitude);
                }
            }
        };
        handler.post(runnable);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    /**
     * Set bottom navigation view with corresponding fragments.
     *
     * @author Ruan Donglin
     */
    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigation_plans:
                        selectedFragment = new PlanFragment();
                        break;
                    case R.id.navigation_community:
                        selectedFragment = new CommunityFragment();
                        break;
                    case R.id.navigation_history:
                        selectedFragment = new HistoryFragment();
                        break;
                    case R.id.navigation_me:
                        selectedFragment = new MeFragment();
                }
                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            };

    /**
     * Get user's current location as latitude and longitude
     */
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isGPSEnabled()) {
                LocationServices.getFusedLocationProviderClient(MainActivity.this)
                        .requestLocationUpdates(locationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                        .removeLocationUpdates(this);

                                if (locationResult != null && locationResult.getLocations().size() > 0) {
                                    int index = locationResult.getLocations().size() - 1;
                                    latitude = locationResult.getLocations().get(index).getLatitude();
                                    longitude = locationResult.getLocations().get(index).getLongitude();
                                    return;
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
     */
    private void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(MainActivity.this)
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
                Toast.makeText(MainActivity.this, "GPS is already turned on", Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(MainActivity.this, 2);
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
     */
    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private void setAllSports() {
        SportAndFacilityDBHelper db = new SportAndFacilityDBHelper(this);
        db.openDatabase();
        allSports = db.getSports();
        db.closeDatabase();
    }

    private List<Sport> testSelectSportRecommended() {
        List<Sport> sports = new ArrayList<>();
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
        c = new Coordinates(latitude, longitude, "current");
        SportsRecommendation sportsRecommendation = new SportsRecommendation(allSports);
        Set<Sport> recommendedSports = sportsRecommendation.getRecommendation(weather.getWeatherData(c));
        sports.clear();
        sports.addAll(recommendedSports);
        return sports;
    }

    private List<Sport> testSelectSportOther() {
        List<Sport> sports = new ArrayList<>();
        sports = allSports;
        sports.removeAll(testSelectSportRecommended());
        return sports;
    }

    public Intent getIntentToPlan() {
        return intentToPlan;
    }


    public Intent getCheckInList() {
        intentToCheckIn = new Intent(MainActivity.this, CheckInNormalActivity.class);
        List<Facility> facilityList = getCheckinFacilitByDistance();
        if (facilityList.size() > 0) {
            intentToCheckIn = new Intent(MainActivity.this, CheckInNormalActivity.class);
            intentToCheckIn.putExtra("ClosestFacility", (Serializable) getCheckinFacilitByDistance().get(0));
            intentToCheckIn.putExtra("FacilityByDistance", (Serializable) getCheckinFacilitByDistance());
        } else {
            intentToCheckIn = new Intent(MainActivity.this, NoFacilityActivity.class);
        }
        intentToCheckIn.putExtra("latitude1", latitude);
        intentToCheckIn.putExtra("longitude1", longitude);
        return intentToCheckIn;
    }

    private List<Facility> getCheckinFacilitByDistance() {
        return FacilityRecommendation.getFacilitiesNearby(MainActivity.this, new Coordinates(latitude, longitude, ""), 5, 20);
    }
}