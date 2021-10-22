package com.example.myapplication.databases;

import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutPlan;
import com.example.myapplication.beans.WorkoutRecord;

import java.util.Date;
import java.util.List;

public class QueryContract {
    public interface WorkoutPlanQuery {
        void createWorkoutPlan(Sport sport, Facility facility);
        void readWorkoutPlan(int _id, WorkoutPlan);
        void readAllWorkoutPlan(WorkoutPlan);
        void updateWorkoutPlan(Sport sport, Facility facility);
        void deleteWorkoutPlan(int _id);
    }

    public interface WorkoutRecordQuery {
        void createWorkoutRecord(Date startTime, Date endTime);
        void readWorkoutRecord(int _id, WorkoutRecord);
        void readAllWorkoutRecord(WorkoutRecord);
        void updateWorkoutRecord(Date startTime, Date endTime);
        void deleteWorkoutRecord(int _id);
    }
}
