package com.example.myapplication.beans;

import java.sql.Time;

public class Workout {
    private Sport sport;
    private Facility facility;
    private float duration;
    private Time startTIme;

    public Workout(Sport sport, Facility facility, float duration, Time startTIme) {
        this.sport = sport;
        this.facility = facility;
        this.duration = duration;
        this.startTIme = startTIme;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Time getStartTIme() {
        return startTIme;
    }

    public void setStartTIme(Time startTIme) {
        this.startTIme = startTIme;
    }
}
