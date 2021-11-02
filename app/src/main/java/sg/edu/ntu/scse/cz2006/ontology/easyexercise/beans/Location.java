package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans;

import java.io.Serializable;

/**
 * A location that can either be a sport facility or a user-defined location.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public abstract class Location extends Coordinates implements Serializable {
    public enum LocationType {
        FACILITY, CUSTOMISED_LOCATION,
    }

    private final LocationType type;

    public Location(double latitude, double longitude, String name,
                    LocationType type) {
        super(latitude, longitude, name);
        this.type = type;
    }

    public LocationType getType() {
        return type;
    }
}
