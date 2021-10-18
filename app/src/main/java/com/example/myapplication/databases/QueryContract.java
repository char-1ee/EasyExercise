package com.example.myapplication.databases;

import android.widget.TableRow;

import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutPlan;
import com.example.myapplication.beans.WorkoutRecord;

import java.util.Date;

public class QueryContract {
    public interface WorkoutPlanQuery {
        void createWorkoutPlan(Sport sport, Facility facility, QueryResponse<Boolean> response);
        void readWorkoutPlan(int _id, QueryResponse<WorkoutPlan> response);
        void readAllWorkoutPlan(QueryResponse<WorkoutPlan> response);
        void updateWorkoutPlan(Sport sport, Facility facility, QueryResponse<Boolean> response);
        void deleteWorkoutPlan(int _id, QueryResponse<Boolean> response);
    }

    public interface WorkoutRecordQuery {
        void createWorkoutRecord(Date startTime, Date endTime, QueryResponse<Boolean> response);
        void readWorkoutRecord(int _id, QueryResponse<WorkoutRecord> response);
        void readAllWorkoutRecord(QueryResponse<WorkoutRecord> response);
        void updateWorkoutRecord(Date startTime, Date endTime, QueryResponse<Boolean> response);
        void deleteWorkoutRecord(int _id, QueryResponse<Boolean> response);
    }

    public interface FacilityQuery {
        void readFacility(String name, QueryResponse<Facility> response);
        void readAllFacility(QueryResponse<Facility> response);
    }

    public interface SportQuery {
//        void readSport(String name, QueryResponse<Sport> response);
        void readAllSport(QueryResponse<Sport> response);
    }
}
