package com.example.myapplication.bean;

import java.util.List;

public class Facility {
    private String name;
    private String website;
    private String telephoneNo;
    private String address;
    private int image;
    private List<Sport> sports;

    private boolean isSelected = false;

    public Facility(String name, String telephoneNo, String address, int image, List<Sport> sports) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.sports = sports;
        this.telephoneNo = telephoneNo;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public int getImage() {
        return image;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
