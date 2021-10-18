package com.example.myapplication.json.weather;

import com.example.myapplication.beans.weather.WeatherDataType;

public class WindSpeed extends RealTimeWeather {
    public WindSpeed(String jsonString) {
        super(WeatherDataType.WIND_SPEED, jsonString);
    }
}
