package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans;

import java.io.Serializable;

/**
 * A user's plan of his/her workout.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class WorkoutPlan extends Workout implements Serializable{

    public WorkoutPlan(Sport sport, Location location, WorkoutStatus status, String planID) {
        super(sport, location, status, planID);
    }

}
