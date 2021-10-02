package com.example.myapplication.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "facility")
public class Facility {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "facilityName")
    private String sportName;

    @ColumnInfo(name = "website")
    private String image;

    @ColumnInfo(name = "telephoneNo")
    private String telephoneNo;

    @ColumnInfo(name = "isSelected")
    private boolean isSelected;

    @ColumnInfo(name = "")
    private boolean isRecommend;
}