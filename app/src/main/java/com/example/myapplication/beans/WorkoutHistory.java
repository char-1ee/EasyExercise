package com.example.myapplication.beans;

import java.sql.Time;
import java.util.Date;

public class WorkoutHistory extends Workout {
    private boolean isPublic;
    private Date date;
    private Time endTime;

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public WorkoutHistory(Sport sport, Facility facility, float duration, Time startTIme, boolean isPublic, Date date) {
        super(sport, facility, duration, startTIme);
        this.date = date;
        this.isPublic = isPublic;
    }
}
