package com.example.myapplication.beans;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class WorkoutPlan implements Serializable {
    public enum WorkoutPlanStatus {
        PRIVATE("Private"), PUBLIC("Public"), JOINED("Joined");

        private final String name;

        WorkoutPlanStatus(String name) {
            this.name = name;
        }

        public static WorkoutPlan.WorkoutPlanStatus getType(String typeString) {
            for (WorkoutPlan.WorkoutPlanStatus type : WorkoutPlan.WorkoutPlanStatus.values()) {
                if (type.toString().equals(typeString)) {
                    return type;
                }
            }
            return null;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
    }

    private Sport sport;
    private Location location;
    private long id;
    private WorkoutPlanStatus status;

    public WorkoutPlan(Sport sport, Location location, long id) {
        this.sport = sport;
        this.location = location;
        this.id = id;
        this.status = WorkoutPlanStatus.PRIVATE;
    }

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
