package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.fragments;

import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.AIR_TEMPERATURE_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.PM25_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.RAINFALL_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.RELATIVE_HUMIDITY_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.UV_INDEX_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.WEATHER_FORECAST_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.WIND_DIRECTION_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather.WIND_SPEED_JSON_URL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Calendar;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.MainActivity;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.ButtonClickUtil;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.RemoteFileIOUtil;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather.Weather;

/**
 * The fragment class for home, including button to start tasks of checking in and making plan.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 * @author Ma Xinyi
 */
public class HomeFragment extends Fragment {
    private Intent intentToCheckIn;
    private Intent intentToPlan;
    private Handler handler;
    private Runnable runnable;
    private MainActivity activity;
    private double latitude;
    private double longitude;
    private Coordinates coordinates;
    private View view;
    private Button buttonMakePlan;
    private Button buttonCheckIn;
    private TextView temperature;
    private TextView pm25;
    private TextView uvIndex;
    private TextView humidity;
    private TextView forecast;
    private Weather weather;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return view;
    }

    private void initView() {
        mSwipeRefreshLayout = view.findViewById(R.id.refreshment_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            activity = (MainActivity) getActivity();
            setWeather(coordinates);
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), "Weather successfully reloaded", Toast.LENGTH_SHORT).show();
        });
        buttonMakePlan = view.findViewById(R.id.home_plan_button);
        buttonCheckIn = view.findViewById(R.id.home_checkin_button);
        temperature = view.findViewById(R.id.temperature);
        pm25 = view.findViewById(R.id.pm25_value);
        uvIndex = view.findViewById(R.id.UV_value);
        humidity = view.findViewById(R.id.Humidity_value);
        forecast = view.findViewById(R.id.Forecast);
        activity = (MainActivity) getActivity();
        assert activity != null;
        longitude = activity.getLongitude();
        latitude = activity.getLatitude();
        initHandler();
        handler.post(runnable);
        TextView greeting = (TextView) view.findViewById(R.id.greeting);
        greeting.setText(getGreeting());
    }

    private void initButton() {
        buttonMakePlan.setOnClickListener(v -> {
            if (!ButtonClickUtil.isFastDoubleClick(R.id.home_plan_button)) {
                startActivity(intentToPlan);
            }
        });

        buttonCheckIn.setOnClickListener(v -> {
            if (!ButtonClickUtil.isFastDoubleClick(R.id.home_checkin_button)) {
                startActivity(intentToCheckIn);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setWeather(Coordinates temp) {
        Thread thread = new Thread(() -> weather = new Weather(
                RemoteFileIOUtil.readFromURL(AIR_TEMPERATURE_JSON_URL),
                RemoteFileIOUtil.readFromURL(RAINFALL_JSON_URL),
                RemoteFileIOUtil.readFromURL(RELATIVE_HUMIDITY_JSON_URL),
                RemoteFileIOUtil.readFromURL(WIND_DIRECTION_JSON_URL),
                RemoteFileIOUtil.readFromURL(WIND_SPEED_JSON_URL),
                RemoteFileIOUtil.readFromURL(UV_INDEX_JSON_URL),
                RemoteFileIOUtil.readFromURL(PM25_JSON_URL),
                RemoteFileIOUtil.readFromURL(WEATHER_FORECAST_JSON_URL)));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ignored) {
        }
        temperature.setText(weather.getWeatherData(temp).getTemperature().getResult().toString());
        pm25.setText(weather.getWeatherData(temp).getPM25().getResult().toString());
        uvIndex.setText(weather.getWeatherData(temp).getUVIndex().toString());
        humidity.setText(weather.getWeatherData(temp).getRelativeHumidity().getResult().toString());
        forecast.setText(weather.getWeatherData(temp).getForecast().getResult());
    }


    private void initHandler() {
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (latitude == 0) {
                    latitude = activity.getLatitude();
                    handler.postDelayed(this, 2000);
                } else {
                    coordinates = new Coordinates(latitude, longitude);
                    intentToPlan = activity.getIntentToPlan();
                    intentToCheckIn = activity.getCheckInList();
                    setWeather(coordinates);
                    initButton();
                }
            }
        };
    }

    private String getGreeting() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        if (hour > 4 && hour <= 12) {
            return "Good morning.";
        } else if (hour > 12 && hour <= 16) {
            return "Good afternoon.";
        } else {
            return "Good evening.";
        }
    }
}
