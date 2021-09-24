package com.example.myapplication.beans;

public class Sport {
    private String text;
    private int image;
    private boolean isSelected = false;
    private boolean inDoor;
    private boolean isRecommended = false;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isInDoor() {
        return inDoor;
    }

    public void setInDoor(boolean inDoor) {
        this.inDoor = inDoor;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }

    public Sport(String text, int image, boolean inDoor) {
        this.text = text;
        this.image = image;
        this.inDoor = inDoor;
    }

}
