package com.example.myapplication.databases;

import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutPlan;
import com.example.myapplication.beans.WorkoutRecord;

import java.util.Date;

public class QueryContract {
    public interface WorkoutPlanQuery {
        void createWorkoutPlan(Sport sport, Facility facility, QueryResponse<Boolean> response);
        void readWorkoutPlan(int _id, QueryResponse<WorkoutPlan> response);
        void updateWorkoutPlan(Sport sport, Facility facility, QueryResponse<Boolean> response);
        void deleteWorkoutPlan(int _id, QueryResponse<Boolean> response);
    }

    public interface WorkoutHistoryQuery {
        void createWorkoutHistory(Date startTime, Date endTime, QueryResponse<Boolean> response);
        void readWorkoutHistory(int _id, QueryResponse<WorkoutRecord> response);
        void updateWorkoutHistory(Date startTime, Date endTime, QueryResponse<Boolean> response);
        void deleteWorkoutHistory(int _id, QueryResponse<Boolean> response);
    }

    // TODO not a final version
    public interface Facility {
        void readFacility(String name, QueryResponse<Facility> response);
    }

    public interface Sport {
        void readSport(String name, QueryResponse<Sport> response);
    }
}
