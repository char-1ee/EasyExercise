package com.example.myapplication.beans;

import java.io.Serializable;

public abstract class Location implements Serializable {
    private Coordinates coordinates;
    private boolean isFacility;

    public Location(Coordinates coordinates, boolean isFacility) {
        this.coordinates = coordinates;
        this.isFacility = isFacility;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isFacility() {
        return isFacility;
    }

    public void setFacility(boolean facility) {
        isFacility = facility;
    }
}
