package sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather.WeatherDataType;

/**
 * Processes the air temperature information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class AirTemperature extends RealTimeWeather {
    public AirTemperature(String jsonString) {
        super(WeatherDataType.AIR_TEMPERATURE, jsonString);
    }
}
