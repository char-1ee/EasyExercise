package com.example.myapplication.beans;

import java.sql.Time;

public class Workout {
    private Sport sport;
    private Location location;
    private float duration;
    private Time startTime;

    public Workout(Sport sport, Location location, float duration, Time startTime) {
        this.sport = sport;
        this.location = location;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
}
