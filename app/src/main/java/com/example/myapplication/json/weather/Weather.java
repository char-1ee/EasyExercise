package com.example.myapplication.json.weather;

import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.weather.WeatherData;

import java.util.Date;

public class Weather {
    public static final String AIR_TEMPERATURE_JSON_URL =
            "https://api.data.gov.sg/v1/environment/air-temperature";
    public static final String RAINFALL_JSON_URL =
            "https://api.data.gov.sg/v1/environment/rainfall";
    public static final String RELATIVE_HUMIDITY_JSON_URL =
            "https://api.data.gov.sg/v1/environment/relative-humidity";
    public static final String WIND_DIRECTION_JSON_URL =
            "https://api.data.gov.sg/v1/environment/wind-direction";
    public static final String WIND_SPEED_JSON_URL =
            "https://api.data.gov.sg/v1/environment/wind-speed";
    /*
    public static final String UV_INDEX_JSON_URL =
            "https://api.data.gov.sg/v1/environment/uv-index";
    public static final String PM25_JSON_URL =
            "https://api.data.gov.sg/v1/environment/pm25";
    public static final String WEATHER_FORECAST_JSON_URL =
            "https://api.data.gov.sg/v1/environment/2-hour-weather-forecast";
     */
    public static final String UV_INDEX_JSON_URL =
            "https://api.data.gov.sg/v1/environment/uv-index?date_time=2021-10-23T13%3A11%3A12";
    public static final String PM25_JSON_URL =
            "https://api.data.gov.sg/v1/environment/pm25?date_time=2021-10-23T13%3A11%3A12";
    public static final String WEATHER_FORECAST_JSON_URL =
            "https://api.data.gov.sg/v1/environment/2-hour-weather-forecast?date_time=2021-10-23T13%3A11%3A12";
    // TODO: This is only a temporary fix. Check data.gov.sg API status.

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
