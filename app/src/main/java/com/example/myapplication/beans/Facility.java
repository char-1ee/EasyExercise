package com.example.myapplication.beans;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
