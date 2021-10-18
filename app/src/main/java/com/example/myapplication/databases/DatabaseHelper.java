package com.example.myapplication.databases;

import static com.example.myapplication.databases.DatabaseContract.DATABASE_NAME;
import static com.example.myapplication.databases.DatabaseContract.DATABASE_VERSION;
import static com.example.myapplication.databases.DatabaseContract.FacilityTable.CREATE_TABLE_FACILITY;
import static com.example.myapplication.databases.DatabaseContract.FacilityTable.DELETE_TABLE_FACILITIES;
import static com.example.myapplication.databases.DatabaseContract.SportTable.CREATE_TABLE_SPORT;
import static com.example.myapplication.databases.DatabaseContract.SportTable.DELETE_TABLE_SPORTS;
import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.CREATE_TABLE_WORKOUT_HISTORY;
import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.DELETE_TABLE_WORKOUT_HISTORY;
import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.CREATE_TABLE_WORKOUT_PLAN;
import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.DELETE_TABLE_WORKOUT_PLAN;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.utils.MyApp;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    private DatabaseHelper() {
        super(MyApp.context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance() {

        if (databaseHelper == null) {
            synchronized (DatabaseHelper.class){ //thread safe singleton
                if (databaseHelper == null)
                    databaseHelper = new DatabaseHelper();
            }
        }

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SPORT);
        db.execSQL(CREATE_TABLE_FACILITY);
        db.execSQL(CREATE_TABLE_WORKOUT_PLAN);
        db.execSQL(CREATE_TABLE_WORKOUT_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_FACILITIES);
        db.execSQL(DELETE_TABLE_SPORTS);
        db.execSQL(DELETE_TABLE_WORKOUT_HISTORY);
        db.execSQL(DELETE_TABLE_WORKOUT_PLAN);
        onCreate(db);
    }

    // TODO delete those generates by Room layer

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion, newVersion);
    }

}
