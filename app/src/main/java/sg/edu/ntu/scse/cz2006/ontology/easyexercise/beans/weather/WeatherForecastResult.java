package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather;

import androidx.annotation.NonNull;

import java.util.Locale;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;

/**
 * A wrapper class for weather forecast results.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class WeatherForecastResult extends WeatherResult {
    private final String result;

    public WeatherForecastResult(Coordinates location,
                                 Double distance, String result) {
        super(WeatherDataType.WEATHER_FORECAST, location, distance);
        this.result = result;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s (%.2fkm away): %s",
                getLocation(), getDistance(), result);
    }

    public String getResult() {
        return result;
    }
}
