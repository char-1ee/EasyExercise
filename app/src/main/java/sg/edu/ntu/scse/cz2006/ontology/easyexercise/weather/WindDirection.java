package sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather.WeatherDataType;

/**
 * Processes the wind direction information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class WindDirection extends RealTimeWeather {
    public WindDirection(String jsonString) {
        super(WeatherDataType.WIND_DIRECTION, jsonString);
    }
}
