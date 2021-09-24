package com.example.myapplication.beans;

import java.sql.Time;

public class Workout {
    private Sport sport;
    private Facility facility;
    private float duration;
    private Time startTIme;

    public Workout(Sport sport, Facility facility, float duration, Time startTIme) {
        this.duration = duration;
        this.sport = sport;
        this.startTIme = startTIme;
        this.facility = facility;
    }

    public Facility getFacility() {
        return facility;
    }

    public float getDuration() {
        return duration;
    }

    public Sport getSport() {
        return sport;
    }

    public Time getStartTIme() {
        return startTIme;
    }
}
