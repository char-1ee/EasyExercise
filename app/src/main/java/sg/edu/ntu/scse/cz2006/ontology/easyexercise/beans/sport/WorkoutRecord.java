package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport;

import java.util.Date;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Location;

/**
 * A user's workout record.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class WorkoutRecord extends Workout {
    private final Date startTime;
    private final Date endTime;
    private final String duration;

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

    public String getDuration() {
        return duration;
    }
}
