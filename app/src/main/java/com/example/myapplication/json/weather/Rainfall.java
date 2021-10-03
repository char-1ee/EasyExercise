package com.example.myapplication.json.weather;

import com.example.myapplication.beans.weather.WeatherDataType;

public class Rainfall extends RealTimeWeather {
    public Rainfall(String jsonString) {
        super(WeatherDataType.RAINFALL, jsonString);
    }
}
