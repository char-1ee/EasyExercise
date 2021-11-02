package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Coordinates;

import java.util.Date;
import java.util.Locale;

/**
 * A collections of both all the realtime weather results and weather forecast result.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class WeatherData {
    private final Coordinates location;
    private final Date timestamp;
    private final RealTimeWeatherResult temperature;
    private final RealTimeWeatherResult rainfall;
    private final RealTimeWeatherResult relativeHumidity;
    private final RealTimeWeatherResult windDirection;
    private final RealTimeWeatherResult windSpeed;
    private final Integer uvIndex;
    private final RealTimeWeatherResult pm25;
    private final WeatherForecastResult forecast;

    public WeatherData(Coordinates location, Date timestamp,
                       RealTimeWeatherResult temperature, RealTimeWeatherResult rainfall,
                       RealTimeWeatherResult relativeHumidity,
                       RealTimeWeatherResult windDirection,
                       RealTimeWeatherResult windSpeed, Integer uvIndex,
                       RealTimeWeatherResult pm25, WeatherForecastResult forecast) {
        this.location = location;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.rainfall = rainfall;
        this.relativeHumidity = relativeHumidity;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.uvIndex = uvIndex;
        this.pm25 = pm25;
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Location: %s\n", location)
                + String.format(Locale.getDefault(), "Timestamp: %s\n",
                timestamp)
                + String.format(Locale.getDefault(), "%s: %s\n",
                temperature.getType(), temperature)
                + String.format(Locale.getDefault(), "%s: %s\n",
                rainfall.getType(), rainfall)
                + String.format(Locale.getDefault(), "%s: %s\n",
                relativeHumidity.getType(), relativeHumidity)
                + String.format(Locale.getDefault(), "%s: %s\n",
                windDirection.getType(), windDirection)
                + String.format(Locale.getDefault(), "%s: %s\n",
                windSpeed.getType(), windSpeed)
                + String.format(Locale.getDefault(), "%s: %d\n",
                WeatherDataType.UV_INDEX, uvIndex)
                + String.format(Locale.getDefault(), "%s: %s\n",
                pm25.getType(), pm25)
                + String.format(Locale.getDefault(), "%s: %s\n",
                forecast.getType(), forecast);
    }

    public Coordinates getLocation() {
        return location;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public RealTimeWeatherResult getTemperature() {
        return temperature;
    }

    public RealTimeWeatherResult getRainfall() {
        return rainfall;
    }

    public RealTimeWeatherResult getRelativeHumidity() {
        return relativeHumidity;
    }

    public RealTimeWeatherResult getWindDirection() {
        return windDirection;
    }

    public RealTimeWeatherResult getWindSpeed() {
        return windSpeed;
    }

    public Integer getUVIndex() {
        return uvIndex;
    }

    public RealTimeWeatherResult getPM25() {
        return pm25;
    }

    public WeatherForecastResult getForecast() {
        return forecast;
    }
}
