package com.example.myapplication.databases.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplication.databases.entities.PlanFacility;
import com.example.myapplication.databases.entities.PlanSport;
import com.example.myapplication.databases.entities.WorkoutPlan;

import java.util.List;

@Dao
public interface WorkoutPlanDao {

    @Transaction
    @Query("SELECT * FROM facility")
    public List<PlanFacility> getPlanFacility();

    @Transaction
    @Query("SELECT * FROM sport")
    public List<PlanSport> getPlanSport();

    @Insert
    void insertPlan(WorkoutPlan workoutPlan);

    @Update
    void updatePlan(WorkoutPlan workoutPlan);

    @Delete
    void deletePlan(WorkoutPlan workoutPlan);

}


