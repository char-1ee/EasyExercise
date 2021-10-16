package com.example.myapplication.beans;

import com.example.myapplication.beans.Coordinates;

public abstract class Location extends Coordinates {
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
