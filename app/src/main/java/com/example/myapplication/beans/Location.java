package com.example.myapplication.beans;

import com.example.myapplication.beans.Coordinates;

import java.io.Serializable;

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
