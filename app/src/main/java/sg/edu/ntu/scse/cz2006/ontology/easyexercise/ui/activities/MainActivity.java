package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.AIR_TEMPERATURE_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.PM25_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.RAINFALL_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.RELATIVE_HUMIDITY_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.UV_INDEX_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.WEATHER_FORECAST_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.WIND_DIRECTION_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.WIND_SPEED_JSON_URL;

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
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

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

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.SportAndFacilityDBHelper;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.recommendation.FacilityRecommendation;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.recommendation.SportsRecommendation;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.fragments.CommunityFragment;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.fragments.HistoryFragment;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.fragments.HomeFragment;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.fragments.PlanFragment;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.fragments.UserFragment;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.Box;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.RemoteFileIOUtil;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather;

public class MainActivity extends AppCompatActivity {
    /**
     * Set bottom navigation view with corresponding fragments.
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
                        selectedFragment = new UserFragment();
                        break;
                }
                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            };

    private Intent intentToPlan;
    private double latitude;
    private double longitude;
    private List<Sport> allSports = new ArrayList<>();
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the title (date, battery, notifications)
        // requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide the title bar (bar below title)
        // getSupportActionBar().hide();

        // Enable full screen
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //         WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // showing the back button in action bar
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        setAllSports();
        initHandler();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Get user's current location as latitude and longitude
     */
    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (isGPSEnabled()) {
                LocationServices.getFusedLocationProviderClient(MainActivity.this)
                        .requestLocationUpdates(locationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                        .removeLocationUpdates(this);

                                int index = locationResult.getLocations().size() - 1;
                                latitude = locationResult.getLocations().get(index).getLatitude();
                                longitude = locationResult.getLocations().get(index).getLongitude();
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
                Toast.makeText(MainActivity.this, "GPS is turned on.", Toast.LENGTH_SHORT).show();
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
                        // Device does not have location info
                        break;
                }
            }
        });
    }

    /**
     * Check if the device's GPS is enabled.
     *
     * @return {@code true} if GPS is enabled; {@code false} otherwise
     */
    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
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

    private List<Sport> selectSportsRecommended() {
        final Box<Weather> boxWeather = new Box<>();
        Thread thread = new Thread(() -> boxWeather.set(new Weather(
                RemoteFileIOUtil.readFromURL(AIR_TEMPERATURE_JSON_URL),
                RemoteFileIOUtil.readFromURL(RAINFALL_JSON_URL),
                RemoteFileIOUtil.readFromURL(RELATIVE_HUMIDITY_JSON_URL),
                RemoteFileIOUtil.readFromURL(WIND_DIRECTION_JSON_URL),
                RemoteFileIOUtil.readFromURL(WIND_SPEED_JSON_URL),
                RemoteFileIOUtil.readFromURL(UV_INDEX_JSON_URL),
                RemoteFileIOUtil.readFromURL(PM25_JSON_URL),
                RemoteFileIOUtil.readFromURL(WEATHER_FORECAST_JSON_URL))));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ignored) {
        }
        Weather weather = boxWeather.get();
        SportsRecommendation sportsRecommendation = new SportsRecommendation(allSports);
        Set<Sport> recommendedSports =
                sportsRecommendation.getRecommendation(
                        weather.getWeatherData(new Coordinates(latitude, longitude, "current")));
        return new ArrayList<>(recommendedSports);
    }

    private List<Sport> selectOtherSports() {
        List<Sport> sports = new ArrayList<>(allSports);
        sports.removeAll(selectSportsRecommended());
        return sports;
    }

    public Intent getIntentToPlan() {
        return intentToPlan;
    }

    public Intent getCheckInList() {
        Intent intentToCheckIn;
        List<Facility> facilityList = getCheckInFacilitiesByDistance();
        if (facilityList.size() > 0) {
            intentToCheckIn = new Intent(MainActivity.this, CheckInNormalActivity.class);
        } else {
            intentToCheckIn = new Intent(MainActivity.this, NoFacilityActivity.class);
        }
        intentToCheckIn.putExtra("ClosestFacility", getCheckInFacilitiesByDistance().get(0));
        intentToCheckIn.putExtra("FacilityByDistance", (Serializable) getCheckInFacilitiesByDistance());
        intentToCheckIn.putExtra("latitude1", latitude);
        intentToCheckIn.putExtra("longitude1", longitude);
        return intentToCheckIn;
    }

    private List<Facility> getCheckInFacilitiesByDistance() {
        return FacilityRecommendation.getFacilitiesNearby(
                MainActivity.this, new Coordinates(latitude, longitude),
                3, 20);
    }

    public void initHandler() {
        getCurrentLocation();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                if (latitude == 0) {
                    handler.postDelayed(this, 2000);
                } else {
                    Toast.makeText(MainActivity.this, "GPS now ready", Toast.LENGTH_SHORT).show();
                    intentToPlan = new Intent(MainActivity.this, SelectSportActivity.class);
                    intentToPlan.putExtra("RecommendedSports", (Serializable) selectSportsRecommended());
                    intentToPlan.putExtra("OtherSports", (Serializable) selectOtherSports());
                    intentToPlan.putExtra("latitude1", latitude);
                    intentToPlan.putExtra("longitude1", longitude);
                }
            }
        };
        handler.post(runnable);
    }
}
