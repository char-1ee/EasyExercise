package com.example.myapplication.json.weather;

import com.example.myapplication.beans.weather.WeatherDataType;

import com.example.myapplication.json.JSONAPI;

public abstract class WeatherJSONAPI implements JSONAPI {
    private final WeatherDataType type;

    public WeatherJSONAPI(WeatherDataType type) {
        this.type = type;
    }

    @Override
    public String getDataTitle() {
        return type.toString();
    }

    public WeatherDataType getType() {
        return type;
    }
}
