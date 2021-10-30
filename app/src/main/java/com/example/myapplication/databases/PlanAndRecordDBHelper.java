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

/**
 * Database helper class for {@code WorkoutPlan} and {@code WorkoutRecord}.
 * TODO: this helper class doesn't help WorkoutPlan and WorkoutRecord.
 *
 * @author Li Xingjian
 */
public class PlanAndRecordDBHelper extends SQLiteOpenHelper {

    private static PlanAndRecordDBHelper databaseHelper;

    /**
     * Accessible constructor.
     */
    public PlanAndRecordDBHelper() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create database.
     * @param db {@link SQLiteDatabase} instance
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WORKOUT_PLAN);
        db.execSQL(CREATE_TABLE_WORKOUT_HISTORY);
    }

    /**
     * Upgrade database.
     * @param db {@link SQLiteDatabase} instance
     * @param oldVersion database old version to be deleted
     * @param newVersion database new version to be created
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_WORKOUT_HISTORY);
        db.execSQL(DELETE_TABLE_WORKOUT_PLAN);
        onCreate(db);
    }

    /**
     * Open database.
     * @param db {@link SQLiteDatabase} instance
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    /**
     * Downgrade database.
     * @param db {@link SQLiteDatabase} instance
     * @param oldVersion database old version to be reduced to
     * @param newVersion database new version to be reduced from
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, newVersion, oldVersion);
    }

}
