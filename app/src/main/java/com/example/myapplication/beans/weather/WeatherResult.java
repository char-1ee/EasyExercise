package com.example.myapplication.beans.weather;

import com.example.myapplication.beans.Coordinates;

public abstract class WeatherResult {
    private final WeatherDataType type;
    private final Coordinates location;
    private final Double distance;

    public WeatherResult(WeatherDataType type, Coordinates location,
                         double distance) {
        this.type = type;
        this.location = location;
        this.distance = distance;
    }

    @Override
    public abstract String toString();

    public WeatherDataType getType() {
        return type;
    }

    public Coordinates getLocation() {
        return location;
    }

    public Double getDistance() {
        return distance;
    }
}
