package com.example.myapplication.beans;

import java.sql.Time;
import java.util.Date;

public class WorkoutHistory extends Workout {
    private boolean isPublic;
    private Date date;
    private Time endTime;

    public WorkoutHistory(Sport sport, Facility facility, float duration, Time startTIme, boolean isPublic, Date date) {
        super(sport, facility, duration, startTIme);
        this.date = date;
        this.isPublic = isPublic;
    }

    public Facility getFacility() {
        return super.getFacility();
    }

    public float getDuration() {
        return super.getDuration();
    }

    public Sport getSport() {
        return super.getSport();
    }

    public Time getStartTIme() {
        return super.getStartTIme();
    }

    public Date getDate() {
        return date;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
