package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;

/**
 * A sport facility for workouts
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class Facility extends Location implements Serializable {
    private final long id;
    private final String name;
    private final String url;
    private final String address;
    private final String postalCode;
    private final String description;
    private final Set<Sport> sports;

    public Facility(long id, String name, String url, String address, String postalCode,
                    String description, double latitude, double longitude) {
        super(latitude, longitude, name, LocationType.FACILITY);
        this.id = id;
        this.name = name;
        this.url = url;
        this.address = address;
        this.postalCode = postalCode;
        this.description = description;
        this.sports = new HashSet<>();
    }

    public Facility(long id, String name, String url, String address, String postalCode,
                    String description, Coordinates coordinates) {
        this(id, name, url, address, postalCode, description,
                coordinates.getLatitude(), coordinates.getLongitude());
    }

    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getURL() {
        return url;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getDescription() {
        return description;
    }

    public Set<Sport> getSports() {
        return sports;
    }

    public void addSport(Sport sport) {
        sports.add(sport);
    }

    @NonNull
    @Override
    public String toString() {
        String stringSports = (sports.isEmpty()) ? "NA"
                : sports.stream().map(Sport::getName)
                .collect(Collectors.joining(", "));
        return String.format(
                "Name: %s\nURL: %s\nAddress: %s\n"
                        + "Postal code: %s\nCoordinates: %s\nSports: %s\n",
                name, url, address, postalCode, getCoordinates(), stringSports);
    }
}
