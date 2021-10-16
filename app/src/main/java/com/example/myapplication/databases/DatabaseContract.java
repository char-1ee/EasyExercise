package com.example.myapplication.databases;

import android.provider.BaseColumns;

public final class DatabaseContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "data.db";
    private static final String INTEGER_TYPE = " INTEGER ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String TEXT_TYPE = " TEXT ";
    private static final String COMMA_SEP = " , ";

    // Not allowed to be instantiated
    private DatabaseContract() {
    }

    // List of all SQL create table statement
    public static final String[] SQL_CREATE_TABLE_LIST = {
            Sport.CREATE_TABLE_SPORT,
            Facility.CREATE_TABLE_FACILITY,
            WorkoutPlan.CREATE_TABLE_WORKOUT_PLAN,
            WorkoutHistory.CREATE_TABLE_WORKOUT_HISTORY
    };

    // List of resource ids for each data file that should be loaded into database
    public static final int[] RAW_IDS = {
            // TODO import date file in R.raw
    };

    public static abstract class Sport implements BaseColumns {
        public static final String TABLE_NAME_SPORTS = " sports ";
        public static final String KEY_NAME = " name ";
        public static final String KEY_ALTERNATIVE_NAME = " alternative_name ";
        public static final String KEY_SPORT_TYPE = " type ";

        public static final String CREATE_TABLE_SPORT = " CREATE TABLE " + TABLE_NAME_SPORTS + " ( " +
                _ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                KEY_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_ALTERNATIVE_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_SPORT_TYPE + TEXT_TYPE + " CHECK ( " + KEY_SPORT_TYPE +
                " IN ( 'Indoor', 'Outdoor', 'Indoor/Outdoor' ) " +
                " ) ";
        // CREATE TABLE sports (
        // _id INTEGER PRIMARY KEY,
        // name TEXT,
        // alternative_name TEXT,
        // type TEXT CHECK ( type IN ( 'Indoor', 'Outdoor', 'Indoor/Outdoor' )
        // )

        public static final String DELETE_TABLE_SPORTS = " DROP TABLE IF EXISTS " + TABLE_NAME_SPORTS;
    }

    public static abstract class Facility implements BaseColumns {
        public static final String TABLE_NAME_FACILITIES = " facilities ";
        public static final String KEY_NAME = " name ";
        public static final String KEY_URL = " url ";
        public static final String KEY_ADDRESS = " address ";
        public static final String KEY_POSTAL_CODE = " postal_code ";
        public static final String KEY_DESCRIPTION = " description ";
        public static final String KEY_SPORTS = " sportId ";

        public static final String CREATE_TABLE_FACILITY = " CREATE TABLE " + TABLE_NAME_FACILITIES + " ( " +
                _ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                KEY_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_URL + TEXT_TYPE + COMMA_SEP +
                KEY_ADDRESS + TEXT_TYPE + COMMA_SEP +
                KEY_POSTAL_CODE + TEXT_TYPE + COMMA_SEP +
                KEY_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                KEY_SPORTS + TEXT_TYPE +
                " ) ";
        // CREATE TABLE facilities (
        // _id INTEGER PRIMARY KEY,
        // name TEXT,
        // url TEXT,
        // address TEXT,
        // postal_code TEXT,
        // description TEXT,
        // sports TEXT
        // );

        public static final String DELETE_TABLE_FACILITIES = "DROP TABLE IF EXISTS " + TABLE_NAME_FACILITIES;
    }

    public static abstract class WorkoutPlan implements BaseColumns {
        public static final String TABLE_NAME_WORKOUT_PLAN = "WorkoutPlan";
        public static final String KEY_SPORT_ID = "sportId";
        public static final String KEY_FACILITY_ID = "facilityId";

        public static final String CREATE_TABLE_WORKOUT_PLAN = "CREATE TABLE " +
                TABLE_NAME_WORKOUT_PLAN + "(" +
                _ID + "INTEGER PRIMARY KEY" +
                KEY_SPORT_ID + TEXT_TYPE + COMMA_SEP +
                KEY_FACILITY_ID + TEXT_TYPE + COMMA_SEP +
                "FOREIGN KEY (" + KEY_SPORT_ID + ") REFERENCES " + Sport.TABLE_NAME_SPORTS + "(id)" +
                "FOREIGN KEY (" + KEY_FACILITY_ID + ") REFERENCES " + Facility.TABLE_NAME_FACILITIES + "(id)" + ")";

        public static final String DELETE_TABLE_WORKOUT_PLAN = "DROP TABLE IF EXISTS " + TABLE_NAME_WORKOUT_PLAN;
    }

    public static abstract class WorkoutHistory implements BaseColumns {
        public static final String TABLE_NAME_WORKOUT_HISTORY = "WorkoutHistory";
        public static final String KEY_SPORT_ID = "sportId";
        public static final String KEY_FACILITY_ID = "facilityId";
        public static final String KEY_START_TIME = "startTime";
        public static final String KEY_END_TIME = "endTime";

        public static final String CREATE_TABLE_WORKOUT_HISTORY = "CREATE TABLE " +
                TABLE_NAME_WORKOUT_HISTORY + "(" +
                _ID + "INTEGER PRIMARY KEY" +
                KEY_SPORT_ID + TEXT_TYPE + COMMA_SEP +
                KEY_FACILITY_ID + TEXT_TYPE + COMMA_SEP +
                KEY_START_TIME + TEXT_TYPE + COMMA_SEP +
                KEY_END_TIME + TEXT_TYPE + COMMA_SEP +
                "FOREIGN KEY (" + KEY_SPORT_ID + ") REFERENCES " + Sport.TABLE_NAME_SPORTS + "(id)" +
                "FOREIGN KEY (" + KEY_FACILITY_ID + ") REFERENCES " + Facility.TABLE_NAME_FACILITIES + "(id)" + ")";

        public static final String DELETE_TABLE_WORKOUT_HISTORY = "DROP TABLE IF EXISTS " + TABLE_NAME_WORKOUT_HISTORY;
    }
}












