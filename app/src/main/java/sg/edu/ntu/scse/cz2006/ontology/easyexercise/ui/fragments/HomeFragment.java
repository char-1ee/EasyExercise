package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.fragments;

import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather.Weather.AIR_TEMPERATURE_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather.Weather.PM25_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather.Weather.RAINFALL_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather.Weather.RELATIVE_HUMIDITY_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather.Weather.UV_INDEX_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather.Weather.WEATHER_FORECAST_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather.Weather.WIND_DIRECTION_JSON_URL;
import static sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather.Weather.WIND_SPEED_JSON_URL;

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

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather.Weather;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.MainActivity;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.utils.ButtonClickUtil;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.utils.IOUtil;

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
    View view;
    Button mMakePlanButton;
    Button mCheckInButton;
    TextView temperature, pm25, uvIndex, humidity, forecast;
    Weather weather;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        latitude = 0;
        longitude = 0;
        initVIew();
        return view;
    }

    private void initVIew() {
        mSwipeRefreshLayout = view.findViewById(R.id.refreshment_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            activity = (MainActivity) getActivity();
            setWeather(c);
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), "Weather successfully reloaded", Toast.LENGTH_SHORT).show();
        });
        mMakePlanButton = view.findViewById(R.id.home_plan_button);
        mCheckInButton = view.findViewById(R.id.home_checkin_button);
        temperature = view.findViewById(R.id.temperature);
        pm25 = view.findViewById(R.id.pm25_value);
        uvIndex = view.findViewById(R.id.UV_value);
        humidity = view.findViewById(R.id.Humidity_value);
        forecast = view.findViewById(R.id.Forecast);
        activity = (MainActivity) getActivity();
        longitude = activity.getLongitude();
        latitude = activity.getLatitude();
        initHandler();
        handler.post(runnable);
    }

    private void initButton() {
        mMakePlanButton.setOnClickListener(v -> {
            if (!ButtonClickUtil.isFastDoubleClick(R.id.home_plan_button)) {
                startActivity(intentToPlan);
            }
        });

        mCheckInButton.setOnClickListener(v -> {
            if (!ButtonClickUtil.isFastDoubleClick(R.id.home_checkin_button)) {
                startActivity(intentToCheckIn);
            }
        });
    }

    private void setWeather(Coordinates temp) {
        Thread thread = new Thread(() -> weather = new Weather(
                IOUtil.readFromURL(AIR_TEMPERATURE_JSON_URL),
                IOUtil.readFromURL(RAINFALL_JSON_URL),
                IOUtil.readFromURL(RELATIVE_HUMIDITY_JSON_URL),
                IOUtil.readFromURL(WIND_DIRECTION_JSON_URL),
                IOUtil.readFromURL(WIND_SPEED_JSON_URL),
                IOUtil.readFromURL(UV_INDEX_JSON_URL),
                IOUtil.readFromURL(PM25_JSON_URL),
                IOUtil.readFromURL(WEATHER_FORECAST_JSON_URL)));
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
                    c = new Coordinates(latitude, longitude, "new");
                    intentToPlan = activity.getIntentToPlan();
                    intentToCheckIn = activity.getCheckInList();
                    setWeather(c);
                    initButton();
                }
            }
        };
    }
}
