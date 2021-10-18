package com.example.myapplication.json.weather;

import com.example.myapplication.beans.weather.WeatherDataType;

public class WindDirection extends RealTimeWeather {
    public WindDirection(String jsonString) {
        super(WeatherDataType.WIND_DIRECTION, jsonString);
    }
}
