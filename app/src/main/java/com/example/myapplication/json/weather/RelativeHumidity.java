package com.example.myapplication.json.weather;

import com.example.myapplication.beans.weather.WeatherDataType;

/**
 * Processes the relative humidity information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class RelativeHumidity extends RealTimeWeather {
    public RelativeHumidity(String jsonString) {
        super(WeatherDataType.RELATIVE_HUMIDITY, jsonString);
    }
}
