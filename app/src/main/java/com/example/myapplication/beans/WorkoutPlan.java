package com.example.myapplication.beans;

import static com.example.myapplication.beans.WorkoutPlan.WorkoutPlanStatus.getType;

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
    private Facility facility;
    private WorkoutPlanStatus status;

    public WorkoutPlan(){}

    public WorkoutPlan(Sport sport, Facility facility, long id) {
        this.sport = sport;
        this.facility = facility;
        this.id = id;
        this.status = WorkoutPlanStatus.PRIVATE;
    }

    public WorkoutPlan(Sport sport, Facility facility, long id, String status) {
        this.sport = sport;
        this.facility = facility;
        this.id = id;
        this.status = getType(status);
    }

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

    public Facility getFacility() {
        return facility;
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

    public void setLocation(Facility facility) {
        this.facility = facility;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(WorkoutPlanStatus status) {
        this.status = status;
    }
}
