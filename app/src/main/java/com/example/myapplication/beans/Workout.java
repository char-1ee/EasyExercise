package com.example.myapplication.beans;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * A user's plan of his/her workout.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class Workout implements Serializable{
    public enum WorkoutStatus{
        PRIVATE("Private"), PUBLIC("Public"), RECORD("Record");

        private final String name;

        WorkoutStatus(String name) {
            this.name = name;
        }

        public static Workout.WorkoutStatus getType(String typeString) {
            for (Workout.WorkoutStatus type : Workout.WorkoutStatus.values()) {
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
    private WorkoutStatus status;
    private String planID;

    public Workout(Sport sport, Location location, WorkoutStatus status, String planID) {
        this.sport = sport;
        this.location = location;
        this.status = status;
        this.planID = planID;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public WorkoutStatus getStatus() {
        return status;
    }

    public void setStatus(WorkoutStatus status) {
        this.status = status;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }
}
