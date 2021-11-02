package sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather.WeatherDataType;

/**
 * Processes the rainfall information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class Rainfall extends RealTimeWeather {
    public Rainfall(String jsonString) {
        super(WeatherDataType.RAINFALL, jsonString);
    }
}
