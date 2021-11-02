package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport;

import androidx.annotation.NonNull;

import java.io.Serializable;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Location;

/**
 * A user's plan of his/her workout.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public abstract class Workout implements Serializable {
    public enum WorkoutStatus {
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

    private final Sport sport;
    private final Location location;
    private final WorkoutStatus status;
    private final String planID;

    public Workout(Sport sport, Location location, WorkoutStatus status, String planID) {
        this.sport = sport;
        this.location = location;
        this.status = status;
        this.planID = planID;
    }

    public Sport getSport() {
        return sport;
    }

    public Location getLocation() {
        return location;
    }

    public WorkoutStatus getStatus() {
        return status;
    }

    public String getPlanID() {
        return planID;
    }
}
