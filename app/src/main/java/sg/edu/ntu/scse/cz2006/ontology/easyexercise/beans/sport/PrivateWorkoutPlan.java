package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport;

import java.io.Serializable;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Location;

/**
 * A user's private plan of his/her workout.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class PrivateWorkoutPlan extends Workout implements Serializable {
    public PrivateWorkoutPlan(Sport sport, Location location, String planID) {
        super(sport, location, WorkoutStatus.PRIVATE, planID);
    }
}
