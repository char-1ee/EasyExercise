package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans;

import java.util.Date;
import java.util.List;

public class PublicPlan extends Workout{

    private int planLimit;
    private Date planStart;
    private Date planFinish;
    private List<String> members;

    public PublicPlan(Sport sport, Location location, String planID, int planLimit, Date planStart, Date planFinish, List<String> members) {
        super(sport, location, WorkoutStatus.PUBLIC, planID);
        this.planLimit = planLimit;
        this.planStart = planStart;
        this.planFinish = planFinish;
        this.members = members;
    }

    public PublicPlan() {
        super(null, null, WorkoutStatus.PUBLIC, "");
    }

    public int getPlanLimit() {
        return planLimit;
    }

    public void setPlanLimit(int planLimit) {
        this.planLimit = planLimit;
    }

    public Date getPlanStart() {
        return planStart;
    }

    public void setPlanStart(Date planStart) {
        this.planStart = planStart;
    }

    public Date getPlanFinish() {
        return planFinish;
    }

    public void setPlanFinish(Date planFinish) {
        this.planFinish = planFinish;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

}
