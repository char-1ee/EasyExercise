package com.example.myapplication.databases.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PlanSport {
    @Embedded private Facility facility;
    @Relation(
            parentColumn = "sportId",
            entityColumn = "sport_id"
    )
    private List<Sport> sportlist;
}
