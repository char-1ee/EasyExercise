package com.example.myapplication.json.weather;

import com.example.myapplication.beans.weather.WeatherDataType;

public class RelativeHumidity extends RealTimeWeather {
    public RelativeHumidity(String jsonString) {
        super(WeatherDataType.RELATIVE_HUMIDITY, jsonString);
    }
}
