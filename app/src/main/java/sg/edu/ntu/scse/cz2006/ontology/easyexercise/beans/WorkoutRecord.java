package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans;

import java.util.Date;

/**
 * A user's workout record.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class WorkoutRecord extends Workout {
    private Date startTime;
    private Date endTime;
    private String duration;

    public WorkoutRecord(Sport sport, Location location, String id, Date startTime, Date endTime, String duration) {
        super(sport, location, WorkoutStatus.RECORD, id);
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
