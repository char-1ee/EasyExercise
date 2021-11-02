package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather;

/**
 * Types of weather data.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
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
     * UV Index value averaged over the past hour. Updated every hour between 7
     * AM and 7 PM everyday.
     */
    UV_INDEX("Ultra-violet Index (UVI)"),

    /**
     * Regional hourly PM2.5 value measured in ug/m3
     */
    PM25("PM2.5"),

    /**
     * Weather forecast for next 2 hours.
     */
    WEATHER_FORECAST("Weather Forecast");

    private final String name;

    WeatherDataType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
