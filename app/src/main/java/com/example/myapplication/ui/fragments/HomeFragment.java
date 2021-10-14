package com.example.myapplication.ui.fragments;

import static com.example.myapplication.json.weather.Weather.AIR_TEMPERATURE_JSON_URL;
import static com.example.myapplication.json.weather.Weather.PM25_JSON_URL;
import static com.example.myapplication.json.weather.Weather.RAINFALL_JSON_URL;
import static com.example.myapplication.json.weather.Weather.RELATIVE_HUMIDITY_JSON_URL;
import static com.example.myapplication.json.weather.Weather.UV_INDEX_JSON_URL;
import static com.example.myapplication.json.weather.Weather.WEATHER_FORECAST_JSON_URL;
import static com.example.myapplication.json.weather.Weather.WIND_DIRECTION_JSON_URL;
import static com.example.myapplication.json.weather.Weather.WIND_SPEED_JSON_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.json.weather.Weather;
import com.example.myapplication.ui.activities.CheckInActivity;
import com.example.myapplication.ui.activities.SelectSportActivity;
import com.example.myapplication.utils.Box;
import com.example.myapplication.utils.IOUtil;

public class HomeFragment extends Fragment {
    View view;
    Button mMakePlanButton;
    Button mCheckInButton;
    TextView temperature, pm25, uvIndex, humidity, forecast;
    String temperature_string, pm25_string, uvIndex_string, humidity_string, forecast_string;
    Coordinates temp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mMakePlanButton = view.findViewById(R.id.home_plan_button);
        mCheckInButton = view.findViewById(R.id.home_checkin_button);

        mMakePlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectSportActivity.class);
                startActivity(intent);
            }
        });
        mCheckInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckInActivity.class);
                startActivity(intent);
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
                IOUtil ioUtil = new IOUtil();
                boxWeather.set(new Weather(
                        ioUtil.readFromURL(AIR_TEMPERATURE_JSON_URL),
                        ioUtil.readFromURL(RAINFALL_JSON_URL),
                        ioUtil.readFromURL(RELATIVE_HUMIDITY_JSON_URL),
                        ioUtil.readFromURL(WIND_DIRECTION_JSON_URL),
                        ioUtil.readFromURL(WIND_SPEED_JSON_URL),
                        ioUtil.readFromURL(UV_INDEX_JSON_URL),
                        ioUtil.readFromURL(PM25_JSON_URL),
                        ioUtil.readFromURL(WEATHER_FORECAST_JSON_URL)));
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            ;
        }

        Weather weather = boxWeather.get();

        temp = new Coordinates(1, 104, "test_location");

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
}
