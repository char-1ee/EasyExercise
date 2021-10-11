package com.example.myapplication.beans;

import java.io.Serializable;

public class WeatherData implements Serializable {
    private float temperature;
    private float humidity;
    private float uvIndex;
    private float rainfall;

    public WeatherData(float temperature, float humidity, float uvIndex, float rainfall) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.uvIndex = uvIndex;
        this.rainfall = rainfall;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(float uvIndex) {
        this.uvIndex = uvIndex;
    }

    public float getRainfall() {
        return rainfall;
    }

    public void setRainfall(float rainfall) {
        this.rainfall = rainfall;
    }
}
