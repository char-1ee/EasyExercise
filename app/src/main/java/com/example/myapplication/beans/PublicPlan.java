package com.example.myapplication.beans;

import java.util.Date;

public class PublicPlan {
    private int planLimit;
    private int currentMember;
    private String planStart;
    private String planFinish;
    private String plan;
    private int sport;
    private int facility;

    public PublicPlan() {
    }

    public PublicPlan(int planLimit, int currentMember, String planStart, String planFinish, String planID, int sportID, int facilityID) {
        this.planLimit = planLimit;
        this.currentMember = currentMember;
        this.planStart = planStart;
        this.planFinish = planFinish;
        this.plan = planID;
        this.sport = sportID;
        this.facility = facilityID;
    }

    public PublicPlan(int limit, Date start, Date finish, int sportID, int facilityID) {
        planLimit = limit;
        planStart = start.toString();
        planFinish = finish.toString();
        sport = sportID;
        facility = facilityID;
        currentMember = 1;
    }

    public int getPlanLimit() {
        return planLimit;
    }

    public void setPlanLimit(int planLimit) {
        this.planLimit = planLimit;
    }

    public int getCurrentMember() {
        return currentMember;
    }

    public void setCurrentMember(int currentMember) {
        this.currentMember = currentMember;
    }

    public String getPlanStart() {
        return planStart;
    }

    public void setPlanStart(String planStart) {
        this.planStart = planStart;
    }

    public String getPlanFinish() {
        return planFinish;
    }

    public void setPlanFinish(String planFinish) {
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
}