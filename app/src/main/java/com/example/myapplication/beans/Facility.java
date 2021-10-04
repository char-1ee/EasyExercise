package com.example.myapplication.beans;

import android.text.TextUtils;

import java.util.List;
import java.util.Map;

public class Facility extends Location {
    private final String name;
    private final String url;
    private final String address;
    private final String postalCode;
    private final String description;
    private final Integer image;
    private final Map<String, String> openingHours;
    private final List<String> facilities;
    private final List<String> sports;

    public Facility(String name, String url, String address, String postalCode,
                    String description, Integer image, Map<String, String> openingHours,
                    List<String> facilities, List<String> sports, double latitude,
                    double longitude) {
        super(latitude, longitude, name, LocationType.FACILITY);
        this.name = name;
        this.url = url;
        this.address = address;
        this.postalCode = postalCode;
        this.description = description;
        this.image = image;
        this.openingHours = openingHours;
        this.facilities = facilities;
        this.sports = sports;
    }

    public Facility(String name, String url, String address, String postalCode,
                    String description, Integer image, Map<String, String> openingHours,
                    List<String> facilities, List<String> sports,
                    Coordinates coordinates) {
        this(name, url, address, postalCode, description, image, openingHours,
                facilities, sports, coordinates.getLatitude(),
                coordinates.getLongitude());
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

    public Integer getImage() {
        return image;
    }

    public Map<String, String> getOpeningHours() {
        return openingHours;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public List<String> getSports() {
        return sports;
    }

    @Override
    public String toString() {
        String stringOpeningHours = "";
        for (var entry : openingHours.entrySet()) {
            if (!stringOpeningHours.isEmpty()) {
                stringOpeningHours += "\n";
            }
            stringOpeningHours +=
                    String.format("%s: %s", entry.getKey(), entry.getValue());
        }
        if (stringOpeningHours.isEmpty()) {
            stringOpeningHours = "Opening hours: NA";
        }
        String stringFacilities =
                (facilities.isEmpty()) ? "NA" : TextUtils.join(", ", facilities);
        String stringSports =
                (sports.isEmpty()) ? "NA" : TextUtils.join(", ", sports);
        return String.format("Name: %s\nURL: %s\nAddress: %s\n"
                        + "Postal code: %s\nCoordinates: %s\n%s\nFacilities: %s\n"
                        + "Sports: %s\n", name, url, address, postalCode,
                getCoordinates(), stringOpeningHours, stringFacilities,
                stringSports);
    }
}
