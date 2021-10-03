package com.example.myapplication.databases.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (primaryKeys = {"facilityId", "sportId"})
public class FacilitySportCrossRef {
    public String facilityId;
    public String sportId;
}
