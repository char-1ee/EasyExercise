package com.example.myapplication.json.weather;

import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.weather.WeatherData;

import java.util.Date;

public class Weather {
    private final RealTimeWeather airTemperature;
    private final RealTimeWeather rainfall;
    private final RealTimeWeather relativeHumidity;
    private final RealTimeWeather windDirection;
    private final RealTimeWeather windSpeed;
    private final UVIndex uvIndex;
    private final PM25 pm25;
    private final WeatherForecast weatherForecast;

    public Weather(String airTemperatureString, String rainfallString,
                   String relativeHumidityString, String windDirectionString,
                   String windSpeedString, String uvIndexString, String pm25String,
                   String weatherForecastString) {
        this.airTemperature = new AirTemperature(airTemperatureString);
        this.rainfall = new Rainfall(rainfallString);
        this.relativeHumidity = new RelativeHumidity(
                relativeHumidityString);
        this.windDirection = new WindDirection(
                windDirectionString);
        this.windSpeed =
                new WindSpeed(windSpeedString);
        this.uvIndex = new UVIndex(uvIndexString);
        this.pm25 = new PM25(pm25String);
        this.weatherForecast = new WeatherForecast(
                weatherForecastString);
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
}
