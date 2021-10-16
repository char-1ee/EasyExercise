package com.example.myapplication.beans;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Facility extends Location {
    private final String name;
    private final String url;
    private final String address;
    private final String postalCode;
    private final String description;
    private final Set<Sport> sports;

    public Facility(String name, String url, String address, String postalCode,
                    String description, double latitude, double longitude) {
        super(latitude, longitude, name, LocationType.FACILITY);
        this.name = name;
        this.url = url;
        this.address = address;
        this.postalCode = postalCode;
        this.description = description;
        this.sports = new HashSet<>();
    }

    public Facility(String name, String url, String address, String postalCode,
                    String description, Coordinates coordinates) {
        this(name, url, address, postalCode, description,
                coordinates.getLatitude(), coordinates.getLongitude());
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

    @Override
    public String toString() {
        String stringSports = (sports.isEmpty()) ? "NA"
                : sports.stream().map(x -> x.getName())
                .collect(Collectors.joining(", "));
        return String.format(
                "Name: %s\nURL: %s\nAddress: %s\n"
                        + "Postal code: %s\nCoordinates: %s\nSports: %s\n",
                name, url, address, postalCode, getCoordinates(), stringSports);
    }
}
