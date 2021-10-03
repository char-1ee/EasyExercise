package com.example.myapplication.databases.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "sport")
public class Sport {

    @PrimaryKey
    public long sportId;

    @ColumnInfo(name = "sport_name")
    public String sportName;

    @ColumnInfo(name = "sport_type")
    public int sportType;

    @ColumnInfo(name = "is_selected")
    public boolean isSelected;

    @ColumnInfo(name = "is_recommend")
    public boolean isRecommend;

    @Ignore
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    public byte[] image;

}