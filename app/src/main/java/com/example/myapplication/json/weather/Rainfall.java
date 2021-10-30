package com.example.myapplication.json.weather;

import com.example.myapplication.beans.weather.WeatherDataType;

/**
 * Processes the rainfall information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class Rainfall extends RealTimeWeather {
    public Rainfall(String jsonString) {
        super(WeatherDataType.RAINFALL, jsonString);
    }
}
