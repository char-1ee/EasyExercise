package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport;

import java.util.Date;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Location;

/**
 * A user's public plan of his/her workout.
 *
 * @author Zhou Yuxuan
 * @author Zhong Ruoyu
 */
public class PublicWorkoutPlan extends Workout {
    private final int planLimit;
    private final Date planStart;
    private final Date planFinish;
    private final List<String> members;

    public PublicWorkoutPlan(Sport sport, Location location, String planID, int planLimit, Date planStart, Date planFinish, List<String> members) {
        super(sport, location, WorkoutStatus.PUBLIC, planID);
        this.planLimit = planLimit;
        this.planStart = planStart;
        this.planFinish = planFinish;
        this.members = members;
    }

    public int getPlanLimit() {
        return planLimit;
    }

    public Date getPlanStart() {
        return planStart;
    }

    public Date getPlanFinish() {
        return planFinish;
    }

    public List<String> getMembers() {
        return members;
    }
}
