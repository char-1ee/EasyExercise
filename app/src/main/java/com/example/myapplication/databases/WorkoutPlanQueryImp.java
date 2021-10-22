package com.example.myapplication.databases;

import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.KEY_FACILITY_ID;
import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.KEY_SPORT_ID;
import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.KEY_STATUS;
import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.TABLE_NAME_WORKOUT_PLAN;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutPlan;

import com.example.myapplication.databases.DatabaseContract.*;

import java.util.ArrayList;
import java.util.List;

public class WorkoutPlanQueryImp {

    private WorkoutPlan workoutPlan;

    public WorkoutPlanQueryImp() {
        this.workoutPlan = new WorkoutPlan();
    }

    public void insert(WorkoutPlan workoutPlan) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SPORT_ID, workoutPlan.getSport().getId());
        values.put(KEY_FACILITY_ID, workoutPlan.getFacility().getId());
        values.put(KEY_STATUS, workoutPlan.getStatus().toString());

        db.insert(TABLE_NAME_WORKOUT_PLAN, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(TABLE_NAME_WORKOUT_PLAN, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<WorkoutPlan> getWorkoutPlanList(Context context) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TABLE_NAME_WORKOUT_PLAN", null);
        if (cursor != null) {
            List<WorkoutPlan> planList = new ArrayList<>();
            if (cursor.getCount()>0) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                    int sportId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SPORT_ID));
                    int facilityId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_FACILITY_ID));
                    String status = cursor.getString(cursor.getColumnIndexOrThrow(KEY_STATUS));

                    Sport sport = new Sport();
                    Facility facility = new Facility();
                    cursorMatchById(context, sport, facility, sportId, facilityId);

                    WorkoutPlan plan = new WorkoutPlan(sport, facility, id, status);
                    planList.add(plan);
                } while (cursor.moveToNext());
            }
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            return planList;
        } else {
            DatabaseManager.getInstance().closeDatabase();
            return null;
        }
    }

    public void cursorMatchById(Context context, Sport sport, Facility facility, int sportId, int facilityId) {
        SportAndFacilityDBHelper dbHelper = new SportAndFacilityDBHelper(context);
        dbHelper.openDatabase();
        sport = dbHelper.getSportById(sportId);
        facility = dbHelper.getFacilityById(facilityId);
        dbHelper.closeDatabase();
    }
}
