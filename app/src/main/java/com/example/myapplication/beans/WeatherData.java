package com.example.myapplication.beans;

public class WeatherData {
    private float temperature;
    private float humidity;
    private float uiIndex;
    private String precipitation;

    public WeatherData(float temperature, float humidity, float uiIndex, String precipitation) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.uiIndex = uiIndex;
        this.precipitation = precipitation;
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

    public float getUiIndex() {
        return uiIndex;
    }

    public void setUiIndex(float uiIndex) {
        this.uiIndex = uiIndex;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }
}
