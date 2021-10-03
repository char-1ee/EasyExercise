package com.example.myapplication.databases.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (primaryKeys = {"facilityId", "sportId"})
public class FacilitySportCrossRef {
    public long facilityId;
    public long sportId;
}
