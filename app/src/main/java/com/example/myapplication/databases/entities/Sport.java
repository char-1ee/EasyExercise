package com.example.myapplication.databases.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    public Sport(int _id, String sportName, byte[] image, int type, boolean isSelected, boolean isRecommend) {
        this._id = _id;
        this.sportName = sportName;
        this.image = image;
        this.type = type;
        this.isSelected = isSelected;
        this.isRecommend = isRecommend;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(boolean recommend) {
        isRecommend = recommend;
    }

    @Ignore
    public Sport(String sportName, byte[] image, int type, boolean isSelected, boolean isRecommend) {
        this.sportName = sportName;
        this.image = image;
        this.type = type;
        this.isSelected = isSelected;
        this.isRecommend = isRecommend;
    }

}