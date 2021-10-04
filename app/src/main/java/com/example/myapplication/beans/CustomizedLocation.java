package com.example.myapplication.beans;

public class CustomizedLocation extends Location {
    public CustomizedLocation(Coordinates coordinates) {
        super(coordinates.getLatitude(), coordinates.getLongitude(),
                coordinates.getName(), LocationType.CUSTOMISED_LOCATION);
    }
}
