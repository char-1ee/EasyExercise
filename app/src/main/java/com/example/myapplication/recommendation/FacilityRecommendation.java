package com.example.myapplication.recommendation;

import android.content.Context;

import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.databases.SportAndFacilityDBHelper;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FacilityRecommendation {
    private FacilityRecommendation() {}

    public static List<Facility> getFacilitiesBySports(Context context, Collection<Sport> sports, Coordinates coordinates, int limit) {
        SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(context);
        manager.openDatabase();
        List<Facility> results = manager.getFacilities().stream()
                .filter(facility ->
                        facility.getSports().stream()
                                .anyMatch(sport ->
                                        sports.stream()
                                                .anyMatch(x -> x.getId() == sport.getId())
                                )
                ).sorted(Comparator.comparingDouble(x -> x.getDistance(coordinates)))
                .limit(limit)
                .collect(Collectors.toList());
        manager.closeDatabase();
        return results;
    }

    public static List<Facility> getFacilitiesNearby(Context context, Coordinates coordinates, double distance, int limit) {
        DBManager manager = new DBManager(context);
        manager.openDatabase();
        List<Facility> results = manager.getFacilities().stream()
                .filter(facility ->
                        coordinates.getDistance(facility.getCoordinates()) <= distance)
                .sorted(Comparator.comparingDouble(x -> x.getDistance(coordinates)))
                .limit(limit)
                .collect(Collectors.toList());
        manager.closeDatabase();
        return results;
    }
}
