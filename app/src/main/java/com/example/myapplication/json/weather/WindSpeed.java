package com.example.myapplication.json.weather;

import com.example.myapplication.beans.weather.WeatherDataType;

/**
 * Processes the wind speed information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class WindSpeed extends RealTimeWeather {
    public WindSpeed(String jsonString) {
        super(WeatherDataType.WIND_SPEED, jsonString);
    }
}
