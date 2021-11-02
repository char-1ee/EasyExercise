package sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather.WeatherDataType;

/**
 * Processes the wind speed information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class WindSpeed extends RealTimeWeather {
    public WindSpeed(String jsonString) {
        super(WeatherDataType.WIND_SPEED, jsonString);
    }
}
