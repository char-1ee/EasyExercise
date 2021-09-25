package com.example.myapplication.beans;

import java.sql.Time;
import java.util.Date;

public class WorkoutHistoryItem extends Workout {
    private boolean isPublic;
    private Date date;
    private Time endTime;

    public WorkoutHistoryItem(Sport sport, Location location, float duration, Time startTime, boolean isPublic, Date date) {
        super(sport, location, duration, startTime);
        this.date = date;
        this.isPublic = isPublic;
    }

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
}
