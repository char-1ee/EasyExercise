package com.example.myapplication.beans.weather;

import com.example.myapplication.beans.Coordinates;

import java.util.Locale;

public class WeatherForecastResult extends WeatherResult {
    private final String result;

    public WeatherForecastResult(Coordinates location,
                                 Double distance, String result) {
        super(WeatherDataType.WEATHER_FORECAST, location, distance);
        this.result = result;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s (%.2fkm away): %s",
                getLocation(), getDistance(), result);
    }

    public String getResult() {
        return result;
    }
}
