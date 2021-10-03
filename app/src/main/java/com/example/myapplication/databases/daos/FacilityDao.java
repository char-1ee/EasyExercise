package com.example.myapplication.databases.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplication.databases.entities.FacilityWithSport;
import com.example.myapplication.databases.entities.Sport;

import java.util.List;

@Dao
public interface FacilityDao {

    /**
     * This method queries the database and returns all of the resulting FacilityWithSport objects
     */
    @Transaction
    @Query("SELECT * FROM facility")
    List<FacilityWithSport> getFacilityWithSport();

    @Insert
    void insertSport(Sport sport);

    @Update
    void updateSport(Sport sport);

    @Delete
    void deleteSport(Sport sport);
}
