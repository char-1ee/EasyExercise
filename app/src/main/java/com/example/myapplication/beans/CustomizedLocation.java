package com.example.myapplication.beans;

import java.io.Serializable;
/**
 * A user-defined location.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class CustomizedLocation extends Location  {
    public CustomizedLocation(Coordinates coordinates) {
        super(coordinates.getLatitude(), coordinates.getLongitude(),
                coordinates.getName(), LocationType.CUSTOMISED_LOCATION);
    }
}
