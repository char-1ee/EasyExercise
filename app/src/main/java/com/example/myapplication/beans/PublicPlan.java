package com.example.myapplication.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublicPlan {
    private int planLimit;
    private long planStart;
    private long planFinish;
    private String plan;
    private int sport;
    private int facility;
    private List<String> members;

    public PublicPlan() {
    }

    public PublicPlan(int planLimit, long planStart, long planFinish, String plan, int sport, int facility, List<String> members) {
        this.planLimit = planLimit;
        this.planStart = planStart;
        this.planFinish = planFinish;
        this.plan = plan;
        this.sport = sport;
        this.facility = facility;
        this.members = members;
    }

    public PublicPlan(int limit, Date start, Date finish, int sportID, int facilityID, String userID) {
        planLimit = limit;
        planStart = start.getTime();
        planFinish = finish.getTime();
        sport = sportID;
        facility = facilityID;
        members = new ArrayList<String>();
        members.add(userID);
    }

    public int getPlanLimit() {
        return planLimit;
    }

    public void setPlanLimit(int planLimit) {
        this.planLimit = planLimit;
    }

    public long getPlanStart() {
        return planStart;
    }

    public void setPlanStart(long planStart) {
        this.planStart = planStart;
    }

    public long getPlanFinish() {
        return planFinish;
    }

    public void setPlanFinish(long planFinish) {
        this.planFinish = planFinish;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public int getSport() {
        return sport;
    }

    public void setSport(int sport) {
        this.sport = sport;
    }

    public int getFacility() {
        return facility;
    }

    public void setFacility(int facility) {
        this.facility = facility;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public void addMembers(String id) {
        this.members.add(id);
    }

    public void removeMembers(int i) {
        this.members.remove(i);
    }
}