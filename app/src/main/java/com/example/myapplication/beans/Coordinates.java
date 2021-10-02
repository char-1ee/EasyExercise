package com.example.myapplication.beans;

public class Coordinates {
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }
}
