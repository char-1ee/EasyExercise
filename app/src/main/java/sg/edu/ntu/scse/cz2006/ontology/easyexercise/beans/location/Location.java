package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location;

import java.io.Serializable;

/**
 * A location that can either be a sport facility or a user-defined location.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public abstract class Location implements Serializable {
    public enum LocationType {
        FACILITY, CUSTOMISED_LOCATION,
    }

    private final LocationType type;
    private final Coordinates coordinates;

    public Location(double latitude, double longitude, String name,
                    LocationType type) {
        this.coordinates = new Coordinates(latitude, longitude, name);
        this.type = type;
    }

    public Coordinates getCoordinates() {
        return new Coordinates(coordinates.getLatitude(), coordinates.getLongitude(), coordinates.getName());
    }

    public double getLatitude() {
        return coordinates.getLatitude();
    }

    public double getLongitude() {
        return coordinates.getLongitude();
    }

    public String getName() {
        return coordinates.getName();
    }

    /**
     * Get the distance from the current location to another.
     *
     * @param coordinates the coordinates of the other location
     * @return the distance between the two locations in kilometres
     * @see Coordinates#getDistance(Coordinates)
     */
    public double getDistance(Coordinates coordinates) {
        return coordinates.getDistance(coordinates);
    }

    public LocationType getType() {
        return type;
    }
}
