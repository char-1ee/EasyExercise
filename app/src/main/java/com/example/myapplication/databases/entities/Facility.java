package com.example.myapplication.databases.entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "facility")
public class Facility {

    @PrimaryKey
    public long facilityId;

    @ColumnInfo(name = "facility_name")
    public String facilityName;

    @ColumnInfo(name = "website")
    public String website;

    @ColumnInfo(name = "telephone_no")
    public String telephoneNo;

    @ColumnInfo(name = "opening_hour")
    public String openingHour;

    @ColumnInfo(name = "is_selected")
    public boolean isSelected;

    @ColumnInfo(name = "address")
    public String address;

    @Embedded
    public Location location;
}