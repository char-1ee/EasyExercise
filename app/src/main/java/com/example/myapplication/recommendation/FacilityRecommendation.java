package com.example.myapplication.recommendation;

import android.content.Context;

import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.databases.SportAndFacilityDBHelper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FacilityRecommendation {
    public static List<Facility> recommend(Context context, Collection<Sport> sports, Coordinates coordinates) {
        SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(context);
        manager.openDatabase();
        List<Facility> facilities = manager.getFacilities().stream().filter(facility -> facility.getSports().stream().anyMatch(sport -> sports.stream().anyMatch(x -> x.getId() == sport.getId()))).collect(Collectors.toList());
        facilities.sort((x, y) -> Double.compare(x.getDistance(coordinates), y.getDistance(coordinates)));
        return facilities;
    }
}