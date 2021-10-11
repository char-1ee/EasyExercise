package com.example.myapplication.beans;

import java.io.Serializable;
import java.util.Date;

public class WorkoutPlan implements Serializable {
    private Sport sport;
    private Location location;
    private long id;
    private WorkoutPlanStatus status;

    public WorkoutPlan(Sport sport, Location location, long id, WorkoutPlanStatus status) {
        this.sport = sport;
        this.location = location;
        this.id = id;
        this.status = status;
    }

    public Sport getSport() {
        return sport;
    }

    public Location getLocation() {
        return location;
    }

    public long getId() {
        return id;
    }

    public WorkoutPlanStatus getStatus() {
        return status;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(WorkoutPlanStatus status) {
        this.status = status;
    }
}
