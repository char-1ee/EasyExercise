package com.example.myapplication.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sport")
public class Sport {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "sportName")
    private String sportName;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "isSelected")
    private boolean isSelected;

    @ColumnInfo(name = "isRecommend")
    private boolean isRecommend;

}