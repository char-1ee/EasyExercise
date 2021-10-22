package com.example.myapplication.databases;

import static com.example.myapplication.databases.DatabaseContract.DATABASE_NAME;
import static com.example.myapplication.databases.DatabaseContract.DATABASE_VERSION;
import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.CREATE_TABLE_WORKOUT_HISTORY;
import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.DELETE_TABLE_WORKOUT_HISTORY;
import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.CREATE_TABLE_WORKOUT_PLAN;
import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.DELETE_TABLE_WORKOUT_PLAN;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.utils.App;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    public DatabaseHelper() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WORKOUT_PLAN);
        db.execSQL(CREATE_TABLE_WORKOUT_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_WORKOUT_HISTORY);
        db.execSQL(DELETE_TABLE_WORKOUT_PLAN);
        onCreate(db);
    }

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
