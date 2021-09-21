package com.example.myapplication.bean;

public class Sport {
    private String text;
    private int image;
    private boolean isSelected = false;
    private boolean inDoor;
    private boolean isRecommended = false;

    public Sport(String text, int image, boolean inDoor) {
        this.text = text;
        this.image = image;
        this.inDoor = inDoor;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }

    public boolean isInDoor() {
        return inDoor;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }

    public boolean isRecommended() {
        return isRecommended;
    }
}
