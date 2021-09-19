package com.example.myapplication;
public class History {
    private int image;
    private String sport;
    private String facility;
    private double duration;

    private boolean isSelected = false;

    public History(String sport, int image, String facility, double duration) {
        this.sport = sport;
        this.image= image;
        this.duration= duration;
        this.facility= facility;
    }

    public int getImage(){
        return image;
    }


    public String getFacility(){
        return facility;
    }

    public double getDuration(){
        return duration;
    }

    public String getSport() {
        return sport;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}

