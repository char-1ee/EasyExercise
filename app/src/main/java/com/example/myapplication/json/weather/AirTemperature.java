package com.example.myapplication.json.weather;

import com.example.myapplication.beans.weather.WeatherDataType;

/**
 * Processes the air temperature information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class AirTemperature extends RealTimeWeather {
    public AirTemperature(String jsonString) {
        super(WeatherDataType.AIR_TEMPERATURE, jsonString);
    }
}
