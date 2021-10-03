package com.example.myapplication.databases.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RecordFacility {
    @Embedded private Facility facility;
    @Relation(
            parentColumn = "facilityId",
            entityColumn = "facility_id"
    )
    private List<Facility> facilityList;
}
