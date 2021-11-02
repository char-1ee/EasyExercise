package sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather.WeatherData;

import java.util.Date;

/**
 * Controls the collection of weather information and provides weather results.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
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
    public static final String UV_INDEX_JSON_URL =
            "https://api.data.gov.sg/v1/environment/uv-index";
    public static final String PM25_JSON_URL =
            "https://api.data.gov.sg/v1/environment/pm25";
    public static final String WEATHER_FORECAST_JSON_URL =
            "https://api.data.gov.sg/v1/environment/2-hour-weather-forecast";

    private final RealTimeWeather airTemperature;
    private final RealTimeWeather rainfall;
    private final RealTimeWeather relativeHumidity;
    private final RealTimeWeather windDirection;
    private final RealTimeWeather windSpeed;
    private final UVIndex uvIndex;
    private final PM25 pm25;
    private final WeatherForecast weatherForecast;

    /**
     * Instantiate a weather controller class.
     *
     * @param airTemperatureString   the air temperature json data as a string
     * @param rainfallString         the rainfall json data as a string
     * @param relativeHumidityString the relative humidity json data as a string
     * @param windDirectionString    the wind direction json data as a string
     * @param windSpeedString        the wind speed json data as a string
     * @param uvIndexString          the UV index json data as a string
     * @param pm25String             the PM2.5 json data as a string
     * @param weatherForecastString  the weather forecast json data as a string
     */
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

    /**
     * Gets weather data at a given location.
     *
     * @param location the location where weather data is needed
     * @return the weather data
     */
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
