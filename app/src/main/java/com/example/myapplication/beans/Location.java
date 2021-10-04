package com.example.myapplication.beans;

public abstract class Location extends Coordinates {
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
