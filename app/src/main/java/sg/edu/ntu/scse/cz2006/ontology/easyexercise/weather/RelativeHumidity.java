package sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather.WeatherDataType;

/**
 * Processes the relative humidity information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class RelativeHumidity extends RealTimeWeather {
    public RelativeHumidity(String jsonString) {
        super(WeatherDataType.RELATIVE_HUMIDITY, jsonString);
    }
}
