package com.example.myapplication.beans;

import com.example.myapplication.databases.SportAndFacilityDBHelper;

import java.io.Serializable;
import java.util.Date;

/**
 * A user's workout record.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class WorkoutRecord extends Workout {
    private Date startTime;
    private Date endTime;

    public WorkoutRecord(Sport sport, Location location, String id, Date startTime, Date endTime) {
        super(sport, location, WorkoutStatus.RECORD, id);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


}
