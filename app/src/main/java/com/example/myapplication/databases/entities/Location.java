package com.example.myapplication.databases.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "location", primaryKeys = {"latitude", "longitude"})
public class Location {
    @PrimaryKey
    public double latitude;
    public double longitude;

    @ColumnInfo(name = "is_facility")
    public boolean isFacility;
}
