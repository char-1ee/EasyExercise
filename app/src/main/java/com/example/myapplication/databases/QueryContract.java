package com.example.myapplication.databases;

import com.example.myapplication.beans.Sport;

public class QueryContract {
    public interface WorkoutPlanQuery {
        void createWorkoutPlan(Sport sport, QueryResponse<Boolean> response);

    }
}
