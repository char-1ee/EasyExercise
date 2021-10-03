package com.example.myapplication.databases.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RecordSport {
    @Embedded private Facility facility;
    @Relation(
            parentColumn = "sportId",
            entityColumn = "sport_id"
    )
    private List<Sport> sportList;
}
