package com.example.myapplication.databases.entities;


import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WorkoutPlan {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @Embedded private Location location;

    private int status;

    private long sport_id;

    private long facility_id;

}
