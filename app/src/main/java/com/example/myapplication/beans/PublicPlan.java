package com.example.myapplication.beans;

import java.util.Date;

public class PublicPlan{
    private int planLimit;
    private int currentMember;
    private Date planDate;
    private int[] memberList;
    private int planID;
    private Sport sport;
    private Facility facility;

    public PublicPlan(int userID, int limit, Date date, Sport s, Facility f) {
        planLimit = limit;
        planDate = date;
        sport = s;
        facility = f;
        currentMember = 1;
        memberList = new int[limit];
        memberList[0] = userID;
    }

    public int getPlanLimit() {
        return planLimit;
    }

    public int getCurrentMember() {
        return currentMember;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public int[] getMemberList() {
        return memberList;
    }

    public int getPlanID() {
        return planID;
    }

    public Sport getSport() {
        return sport;
    }

    public Facility getFacility() {
        return facility;
    }
}
