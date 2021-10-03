package com.example.myapplication.databases.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (primaryKeys = {"facilityName", "sportName"})
public class FacilitySportCrossRef {
    public String facilityName;
    public String sportName;
}
