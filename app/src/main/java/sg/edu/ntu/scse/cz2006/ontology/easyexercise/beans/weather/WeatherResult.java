package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Coordinates;

/**
 * A weather result for a particular weather type.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public abstract class WeatherResult {
    private final WeatherDataType type;
    private final Coordinates location;
    private final Double distance;

    public WeatherResult(WeatherDataType type, Coordinates location,
                         double distance) {
        this.type = type;
        this.location = location;
        this.distance = distance;
    }

    @Override
    public abstract String toString();

    public WeatherDataType getType() {
        return type;
    }

    public Coordinates getLocation() {
        return location;
    }

    public Double getDistance() {
        return distance;
    }
}
