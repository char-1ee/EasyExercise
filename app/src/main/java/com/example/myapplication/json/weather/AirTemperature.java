package com.example.myapplication.json.weather;

import com.example.myapplication.beans.weather.WeatherDataType;

public class AirTemperature extends RealTimeWeather {
    public AirTemperature(String jsonString) {
        super(WeatherDataType.AIR_TEMPERATURE, jsonString);
    }
}
