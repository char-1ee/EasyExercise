package com.example.myapplication.beans.weather;

import com.example.myapplication.beans.Coordinates;

import java.util.Locale;

public class RealTimeWeatherResult extends WeatherResult {
    private final Double result;

    public RealTimeWeatherResult(WeatherDataType type, Coordinates location,
                                 Double distance, Double result) {
        super(type, location, distance);
        if (type == WeatherDataType.WEATHER_FORECAST) {
            throw new RuntimeException(
                    "Initialising RealTimeWeatherResult with WEATHER_FORECAST");
        }
        this.result = result;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s (%.2fkm away): %.1f",
                getLocation(), getDistance(), result);
    }

    public Double getResult() {
        return result;
    }
}
