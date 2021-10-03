package com.example.myapplication.json.weather;

import android.content.Context;

import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.weather.WeatherData;
import com.example.myapplication.utils.IOUtil;

import java.util.Date;

public class Weather {
    private final IOUtil ioUtil;

    private RealTimeWeather airTemperature;
    private RealTimeWeather rainfall;
    private RealTimeWeather relativeHumidity;
    private RealTimeWeather windDirection;
    private RealTimeWeather windSpeed;
    private UVIndex uvIndex;
    private PM25 pm25;
    private WeatherForecast weatherForecast;

    private final String airTemperaturePath;
    private final String rainfallPath;
    private final String relativeHumidityPath;
    private final String windDirectionPath;
    private final String windSpeedPath;
    private final String uvIndexPath;
    private final String pm25Path;
    private final String weatherForecastPath;

    public Weather(Context context, String airTemperaturePath, String rainfallPath,
                   String relativeHumidityPath, String windDirectionPath,
                   String windSpeedPath, String uvIndexPath, String pm25Path,
                   String weatherForecastPath) {
        this.ioUtil = new IOUtil(context);

        this.airTemperaturePath = airTemperaturePath;
        this.rainfallPath = rainfallPath;
        this.relativeHumidityPath = relativeHumidityPath;
        this.windDirectionPath = windDirectionPath;
        this.windSpeedPath = windSpeedPath;
        this.uvIndexPath = uvIndexPath;
        this.pm25Path = pm25Path;
        this.weatherForecastPath = weatherForecastPath;

        this.airTemperature = new AirTemperature(
                ioUtil.convertFileToString(airTemperaturePath));
        this.rainfall = new Rainfall(ioUtil.convertFileToString(rainfallPath));
        this.relativeHumidity = new RelativeHumidity(
                ioUtil.convertFileToString(relativeHumidityPath));
        this.windDirection = new WindDirection(
                ioUtil.convertFileToString(windDirectionPath));
        this.windSpeed =
                new WindSpeed(ioUtil.convertFileToString(windSpeedPath));
        this.uvIndex = new UVIndex(ioUtil.convertFileToString(uvIndexPath));
        this.pm25 = new PM25(ioUtil.convertFileToString(pm25Path));
        this.weatherForecast = new WeatherForecast(
                ioUtil.convertFileToString(weatherForecastPath));
    }

    public WeatherData getWeatherData(Coordinates location) {
        return new WeatherData(location, new Date(),
                airTemperature.getReading(location),
                rainfall.getReading(location),
                relativeHumidity.getReading(location),
                windDirection.getReading(location),
                windSpeed.getReading(location), uvIndex.getValue(),
                pm25.getReading(location),
                weatherForecast.getForecast(location));
    }

    public void refreshData() {
        airTemperature = new AirTemperature(
                ioUtil.convertFileToString(airTemperaturePath));
        rainfall = new Rainfall(ioUtil.convertFileToString(rainfallPath));
        relativeHumidity = new RelativeHumidity(
                ioUtil.convertFileToString(relativeHumidityPath));
        windDirection = new WindDirection(
                ioUtil.convertFileToString(windDirectionPath));
        windSpeed = new WindSpeed(ioUtil.convertFileToString(windSpeedPath));
        uvIndex = new UVIndex(ioUtil.convertFileToString(uvIndexPath));
        pm25 = new PM25(ioUtil.convertFileToString(pm25Path));
        weatherForecast = new WeatherForecast(
                ioUtil.convertFileToString(weatherForecastPath));
    }
}
