package com.example.myapplication.utils.comparators;

import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;

import java.util.Comparator;

public class FacilityComparator implements Comparator<Facility> {
    private final Coordinates location;

    public FacilityComparator(Coordinates location) {
        this.location = location;
    }

    @Override
    public int compare(Facility lhs, Facility rhs) {
        return Double.compare(lhs.getCoordinates().getDistance(location),
                rhs.getCoordinates().getDistance(location));
    }
}
