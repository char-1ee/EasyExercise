package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather;

import androidx.annotation.NonNull;

import java.util.Locale;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;

/**
 * A wrapper class for real time weather results.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class RealTimeWeatherResult extends WeatherResult {
    private final Double result;

    public RealTimeWeatherResult(WeatherDataType type, Coordinates location,
                                 Double distance, Double result) {
        super(type, location, distance);
        if (type == WeatherDataType.WEATHER_FORECAST) {
            throw new RuntimeException(
                    "Initialising RealTimeWeatherResult with WEATHER_FORECAST");
        }
        this.result = result;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s (%.2fkm away): %.1f",
                getLocation(), getDistance(), result);
    }

    public Double getResult() {
        return result;
    }
}
