package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.beans;

import java.util.Date;

public class WorkoutRecord extends WorkoutPlan {
    private Date startTime;
    private Date endTime;

    public WorkoutRecord(Sport sport, Location location, long id, WorkoutPlanStatus status, Date startTime, Date endTime) {
        super(sport, location, id, status);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
