package com.example.myapplication.beans;

import java.sql.Time;

public class WorkoutPlan extends Workout {
    private long id;
    private WorkoutPlanStatus status;

    public WorkoutPlan(Sport sport,
                       Location location,
                       float duration,
                       Time startTime,
                       long id,
                       WorkoutPlanStatus status) {
        super(sport, location, duration, startTime);
        this.id = id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WorkoutPlanStatus getStatus() {
        return status;
    }

    public void setStatus(WorkoutPlanStatus status) {
        this.status = status;
    }

    public void complete() {
        status = WorkoutPlanStatus.COMPLETED;
    }

    public void start() {
        status = WorkoutPlanStatus.STARTED;
    }
}
