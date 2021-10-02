package com.example.myapplication.json.weather;

public enum WeatherDataType {
    /**
     * Minute-by-minute air temperature readings at weather-station level.
     */
    AIR_TEMPERATURE("Air Temperature"),

    /**
     * Precipitation readings at weather-station level, updated every five
     * minutes.
     */
    RAINFALL("Rainfall"),

    /**
     * Minute-by-minute relative humidity readings at weather-station level.
     */
    RELATIVE_HUMIDITY("Relative Humidity"),

    /**
     * Minute-by-minute wind direction readings at weather-station level.
     */
    WIND_DIRECTION("Wind Direction"),

    /**
     * Minute-by-minute wind speed readings at weather-station level.
     */
    WIND_SPEED("Wind Speed"),

    /**
     * Weather forecast for next 2 hours, next 24 hours and next 4 days.
     */
    WEATHER_FORECAST("Weather Forecast");

    private final String name;

    WeatherDataType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
