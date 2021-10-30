package com.example.myapplication.json.weather;

import com.example.myapplication.beans.weather.WeatherDataType;

/**
 * Processes the wind direction information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class WindDirection extends RealTimeWeather {
    public WindDirection(String jsonString) {
        super(WeatherDataType.WIND_DIRECTION, jsonString);
    }
}
