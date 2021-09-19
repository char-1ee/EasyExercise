package com.example.myapplication;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkOutHistory extends WorkOut{

    private boolean isPublic;
    private Date date;
    private Time endTime;



    public WorkOutHistory(Sport sport, Facility facility, float duration, Time startTIme, boolean isPublic, Date date){
        super(sport, facility, duration, startTIme);
        this.date= date;
        this.isPublic= isPublic;
    }


    public Facility getFacility() {
        return super.getFacility();
    }


    public float getDuration() {
        return super.getDuration();
    }


    public Sport getSport() {
        return super.getSport();
    }


    public Time getStartTIme() {
        return super.getStartTIme();
    }

    public Date getDate() {
        return date;
    }

    public boolean isPublic() {
        return isPublic;
    }
}

