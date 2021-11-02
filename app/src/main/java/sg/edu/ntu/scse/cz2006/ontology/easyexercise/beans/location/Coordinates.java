package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

/**
 * Geographical coordinates of a location.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class Coordinates implements Serializable {
    private static final double R = 6371; // radius of the earth in km

    private final double latitude;
    private final double longitude;
    private final String name;

    public Coordinates(double latitude, double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public Coordinates(double latitude, double longitude) {
        this(latitude, longitude, "");
    }

    /**
     * Get the distance from the current location to another.
     *
     * @param coordinates the coordinates of the other location
     * @return the distance between the two locations in kilometres
     * @see #getDistance(double, double)
     */
    public double getDistance(Coordinates coordinates) {
        return getDistance(coordinates.latitude, coordinates.longitude);
    }

    /**
     * Get the distance from the current location to another.
     *
     * @param latitude  the latitude of the other location
     * @param longitude the longitude of the other location
     * @return the distance between the two locations in kilometres
     * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html">
     * http://www.movable-type.co.uk/scripts/latlong.html</a>
     */
    public double getDistance(double latitude, double longitude) {
        double dLatitude = Math.toRadians(this.latitude - latitude);
        double dLongitude = Math.toRadians(this.longitude - longitude);
        double a = Math.sin(dLatitude / 2) * Math.sin(dLatitude / 2)
                + Math.cos(Math.toRadians(this.latitude))
                * Math.cos(Math.toRadians(latitude))
                * Math.sin(dLongitude / 2) * Math.sin(dLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public Coordinates getCoordinates() {
        return new Coordinates(latitude, longitude, name);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        if (name.isEmpty()) {
            return String.format("[%s, %s]", latitudeToString(),
                    longitudeToString());
        }
        return String.format("[%s: %s, %s]", name, latitudeToString(),
                longitudeToString());
    }

    public String latitudeToString() {
        return String.format(Locale.getDefault(), "%f%s", Math.abs(latitude),
                latitude >= 0 ? "N" : "S");
    }

    public String longitudeToString() {
        return String.format(Locale.getDefault(), "%f%s", Math.abs(longitude),
                longitude >= 0 ? "E" : "W");
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Coordinates) {
            Coordinates otherCoordinates = (Coordinates) other;
            return this == other ||
                    this.latitude == otherCoordinates.latitude &&
                            this.longitude == otherCoordinates.longitude;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, name);
    }
}
