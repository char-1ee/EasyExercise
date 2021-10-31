package com.example.myapplication.databases;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.CustomizedLocation;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.PublicPlan;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.Workout;
import com.example.myapplication.beans.WorkoutPlan;
import com.example.myapplication.beans.WorkoutRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutDatabaseManager {

    public static WorkoutPlan toWorkoutPlan(FirebaseWorkoutPlan firebasePlan, Context context){
        SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(context);
        manager.openDatabase();
        Sport s = manager.getSportById(firebasePlan.getSport());
        Location l;
        if(firebasePlan.isCustomized()){
            Coordinates c = new Coordinates(firebasePlan.getLatitude(), firebasePlan.getLongitude(), firebasePlan.getName());
            l = new CustomizedLocation(c);
        }
        else{
            l = manager.getFacilityById(firebasePlan.getFacility());
        }
        manager.closeDatabase();
        WorkoutPlan workoutPlan = new WorkoutPlan(
                s, l, Workout.WorkoutStatus.PRIVATE, firebasePlan.getPlanID()
        );
        return workoutPlan;
    }

    public static PublicPlan toPublicPlan(FirebasePublicPlan firebasePlan, Context context){
        SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(context);
        manager.openDatabase();
        Sport s = manager.getSportById(firebasePlan.getSport());
        Location l = manager.getFacilityById(firebasePlan.getFacility());
        manager.closeDatabase();
        PublicPlan workoutPlan = new PublicPlan(
                s, l, firebasePlan.getPlan(), firebasePlan.getPlanLimit(),
                new Date(firebasePlan.getPlanStart()), new Date(firebasePlan.getPlanFinish()),
                firebasePlan.getMembers());
        return workoutPlan;
    }


    public class FirebaseWorkoutRecord {
        private int sport;
        private String planID;
        private boolean customized;
        private int facility;
        private double longitude;
        private double latitude;
        private long startTime;
        private long endTime;
        private String name;

        public FirebaseWorkoutRecord(WorkoutRecord record, String id) {
            sport = record.getSport().getId();
            planID = id;
            customized = (record.getLocation().getType() == Location.LocationType.CUSTOMISED_LOCATION);
            if(customized){
                longitude = record.getLocation().getLongitude();
                latitude = record.getLocation().getLatitude();
                name = record.getLocation().getName();
                facility = -1;
            }
            else{
                longitude = -1;
                latitude = -1;
                name = "";
                facility = ((Facility) record.getLocation()).getId();
            }
            startTime = record.getStartTime().getTime();
            endTime = record.getEndTime().getTime();
        }

        public FirebaseWorkoutRecord() {
        }

        public int getSport() {
            return sport;
        }

        public String getPlanID() {
            return planID;
        }

        public boolean isCustomized() {
            return customized;
        }

        public int getFacility() {
            return facility;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public long getStartTime() {
            return startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public String getName() {
            return name;
        }
    }

    public static class FirebasePublicPlan {
        private int planLimit;
        private long planStart;
        private long planFinish;
        private String plan;
        private int sport;
        private int facility;
        private List<String> members;

        public FirebasePublicPlan() {
        }

        public FirebasePublicPlan(int planLimit, long planStart, long planFinish, String plan, int sport, int facility, List<String> members) {
            this.planLimit = planLimit;
            this.planStart = planStart;
            this.planFinish = planFinish;
            this.plan = plan;
            this.sport = sport;
            this.facility = facility;
            this.members = members;
        }

        public FirebasePublicPlan(int limit, Date start, Date finish, int sportID, int facilityID, String userID) {
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


    public static class FirebaseWorkoutPlan{
        private int sport;
        private String planID;
        private boolean customized;
        private int facility;
        private double longitude;
        private double latitude;
        private String name;

        public FirebaseWorkoutPlan(){}

        public FirebaseWorkoutPlan(WorkoutPlan plan, String id) {
            sport = plan.getSport().getId();
            planID = id;
            customized = (plan.getLocation().getType() == Location.LocationType.CUSTOMISED_LOCATION);
            if(customized){
                longitude = plan.getLocation().getLongitude();
                latitude = plan.getLocation().getLatitude();
                name = plan.getLocation().getName();
                facility = -1;
            }
            else{
                longitude = -1;
                latitude = -1;
                name = "";
                facility = ((Facility) plan.getLocation()).getId();
            }
        }

        public FirebaseWorkoutPlan(int sport, String planID, boolean customized, int facility, double longitude, double latitude, String name) {
            this.sport = sport;
            this.planID = planID;
            this.customized = customized;
            this.facility = facility;
            this.longitude = longitude;
            this.latitude = latitude;
            this.name = name;
        }

        public int getSport() {
            return sport;
        }

        public String getPlanID() {
            return planID;
        }

        public boolean isCustomized() {
            return customized;
        }

        public int getFacility() {
            return facility;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public String getName() {
            return name;
        }
    }
}
