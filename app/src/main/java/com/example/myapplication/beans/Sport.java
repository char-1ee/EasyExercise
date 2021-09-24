package com.example.myapplication.beans;

public class Sport {
    private String name;
    private int image;
    private boolean isIndoor;
    private boolean isSelected = false;
    private boolean isRecommended = false;

    public Sport(String name, int image, boolean isIndoor) {
        this.name = name;
        this.image = image;
        this.isIndoor = isIndoor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isIndoor() {
        return isIndoor;
    }

    public void setIndoor(boolean indoor) {
        this.isIndoor = indoor;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }
}
