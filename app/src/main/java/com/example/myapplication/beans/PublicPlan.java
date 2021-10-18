package com.example.myapplication.beans;

import java.util.Date;

public class PublicPlan{
    private int planLimit;
    private int currentMember;
    private String planStart;
    private String planFinish;
    //private int[] memberList;
    private int planID;
    private int sport_id;
    private int facility_id;

    public PublicPlan(int planLimit, int currentMember, String planStart, String planFinish, int planID, int sport_id, int facility_id) {
        this.planLimit = planLimit;
        this.currentMember = currentMember;
        this.planStart = planStart;
        this.planFinish = planFinish;
        this.planID = planID;
        this.sport_id = sport_id;
        this.facility_id = facility_id;
    }

    public PublicPlan(int userID, int limit, Date start, Date finish, int sport, int facility) {
        planLimit = limit;
        planStart = start.toString();
        planFinish = finish.toString();
        sport_id = sport;
        facility_id = facility;
        currentMember = 1;
        //memberList = new int[limit];
        //memberList[0] = userID;
    }

    public int getPlanLimit() {
        return planLimit;
    }

    public int getCurrentMember() {
        return currentMember;
    }

    public String getPlanStart() {
        return planStart;
    }

    public String getPlanFinish() {
        return planFinish;
    }

    //public int[] getMemberList() {
    //    return memberList;
    //}

    public int getPlanID() {
        return planID;
    }

    public int getSportID() {
        return sport_id;
    }

    public int getFacilityID() {
        return facility_id;
    }
}
