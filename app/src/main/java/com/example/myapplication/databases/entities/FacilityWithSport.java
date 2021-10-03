package com.example.myapplication.databases.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class FacilityWithSport {
    @Embedded
    public Facility facility;
    @Relation(
            parentColumn = "facilityId",
            entityColumn = "sportId",
            associateBy = @Junction(FacilitySportCrossRef.class)
    )
    public List<Sport> sportSupported;
}
