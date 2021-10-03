package com.example.myapplication.beans;

import java.util.Locale;

public class Coordinates {
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

    public double getDistance(Coordinates coordinates) {
        return getDistance(coordinates.latitude, coordinates.longitude);
    }

    /**
     * Get the distance from the current location to another.
     *
     * @param latitude the latitude of the other location
     * @param longitude the longitude of the other location
     * @return the distance between the two locations in kilometres
     * @see http://www.movable-type.co.uk/scripts/latlong.html
     */
    public double getDistance(double latitude, double longitude) {
        double dLatitude = Math.toRadians(this.latitude - latitude);
        double dLongitude = Math.toRadians(this.longitude - longitude);
        double a = Math.sin(dLatitude / 2) * Math.sin(dLatitude / 2)
                + Math.cos(Math.toRadians(this.latitude))
                * Math.cos(Math.toRadians(latitude))
                * Math.sin(dLongitude / 2) * Math.sin(dLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
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

    @Override
    public String toString() {
        if (name == null) {
            return String.format("[%s, %s]", latitudeToString(),
                    longitudeToString());
        }
        return String.format("[%s: %s, %s]", name, latitudeToString(),
                longitudeToString());
    }

    public String latitudeToString() {
        return String.format(Locale.getDefault(), "%f%s", Math.abs(latitude),
                latitude >= 0 ? "E" : "W");
    }

    public String longitudeToString() {
        return String.format(Locale.getDefault(), "%f%s", Math.abs(longitude),
                longitude >= 0 ? "N" : "S");
    }
}
