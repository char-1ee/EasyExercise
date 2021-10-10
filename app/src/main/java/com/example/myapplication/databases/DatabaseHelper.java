package com.example.myapplication.databases;

import static com.example.myapplication.databases.DatabaseContract.*;
import static com.example.myapplication.databases.DatabaseContract.Facility.*;
import static com.example.myapplication.databases.DatabaseContract.Sport.*;
import static com.example.myapplication.databases.DatabaseContract.WorkoutHistory.*;
import static com.example.myapplication.databases.DatabaseContract.WorkoutPlan.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;
//    private DatabaseHelper databaseHelper;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        db.execSQL(DELETE_TABLE_FACILITY);
        db.execSQL(DELETE_TABLE_SPORT);
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
