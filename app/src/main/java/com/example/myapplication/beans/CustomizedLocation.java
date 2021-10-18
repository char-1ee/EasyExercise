package com.example.myapplication.beans;

import java.io.Serializable;

public class CustomizedLocation extends Location  {
    public CustomizedLocation(Coordinates coordinates) {
        super(coordinates.getLatitude(), coordinates.getLongitude(),
                coordinates.getName(), LocationType.CUSTOMISED_LOCATION);
    }
}
