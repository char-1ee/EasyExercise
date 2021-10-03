package com.example.myapplication.databases.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WorkoutRecord {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @Embedded
    private Location location;

    private int status;

    private long sport_id;

    private long facility_id;

    private long start_time;

    private long end_time;

}