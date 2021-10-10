package com.example.myapplication.databases;

import android.provider.BaseColumns;

import com.example.myapplication.R;

public final class DatabaseContract {
    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "database.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String COMMA_SEP          = ",";

    // Not allowed to be instantiated
    private DatabaseContract() {}

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
        public static final String TABLE_NAME_SPORT = "Sport";
        public static final String KEY_NAME = "name";
        public static final String KEY_SPORT_TYPE = "sportType";

        public static final String CREATE_TABLE_SPORT = "CREATE TABLE " +
                TABLE_NAME_SPORT + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_SPORT_TYPE + TEXT_TYPE + "CHECK ( " + KEY_SPORT_TYPE +
                " IN ( 'Indoor', 'Outdoor', 'Indoor/Outdoor' ) " + ")";

        public static final String DELETE_TABLE_SPORT = "DROP TABLE IF EXISTS " + TABLE_NAME_SPORT;
    }

    public static abstract class Facility implements BaseColumns {
        public static final String TABLE_NAME_FACILITY = "Facility";
        public static final String KEY_NAME = "name";
        public static final String KEY_ADDRESS = "address";
        public static final String KEY_POSTAL_CODE = "postalCode";
        public static final String KEY_DESCRIPTION = "description";
        public static final String KEY_URL = "url";
        public static final String KEY_OPENING_HOUR = "openingHour";
        public static final String KEY_SPORT_ID = "sportId";
        // TODO need List<String> facilities?

        public static final String CREATE_TABLE_FACILITY = "CREATE TABLE " +
                TABLE_NAME_FACILITY + " (" +
                _ID + "INTEGER PRIMARY KEY," +
                KEY_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_ADDRESS + TEXT_TYPE + COMMA_SEP +
                KEY_POSTAL_CODE + TEXT_TYPE + COMMA_SEP +
                KEY_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                KEY_URL + TEXT_TYPE + COMMA_SEP +
                KEY_OPENING_HOUR + TEXT_TYPE + COMMA_SEP +
                KEY_SPORT_ID + TEXT_TYPE + COMMA_SEP +
                "FOREIGN KEY (" + KEY_SPORT_ID + ") REFERENCES " + Sport.TABLE_NAME_SPORT + "(id)" + ")";

        public static final String DELETE_TABLE_FACILITY = "DROP TABLE IF EXISTS " + TABLE_NAME_FACILITY;
    }

    public static abstract class WorkoutPlan implements BaseColumns {
        public static final String TABLE_NAME_WORKOUT_PLAN = "WorkoutPlan";
        public static final String KEY_SPORT_ID = "sportId";
        public static final String KEY_FACILITY_ID = "facilityId";
//        public static final String KEY_LOCATION = "location";
        // TODO haven not add PlanStatus yet

        public static final String CREATE_TABLE_WORKOUT_PLAN = "CREATE TABLE " +
                TABLE_NAME_WORKOUT_PLAN + "(" +
                _ID + "INTEGER PRIMARY KEY" +
                KEY_SPORT_ID + TEXT_TYPE + COMMA_SEP +
                KEY_FACILITY_ID + TEXT_TYPE + COMMA_SEP +
                "FOREIGN KEY (" + KEY_SPORT_ID + ") REFERENCES " + Sport.TABLE_NAME_SPORT + "(id)" +
                "FOREIGN KEY (" + KEY_FACILITY_ID + ") REFERENCES " + Facility.TABLE_NAME_FACILITY + "(id)" + ")";

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
                "FOREIGN KEY (" + KEY_SPORT_ID + ") REFERENCES " + Sport.TABLE_NAME_SPORT + "(id)" +
                "FOREIGN KEY (" + KEY_FACILITY_ID + ") REFERENCES " + Facility.TABLE_NAME_FACILITY + "(id)" + ")";

        public static final String DELETE_TABLE_WORKOUT_HISTORY = "DROP TABLE IF EXISTS " + TABLE_NAME_WORKOUT_HISTORY;
    }
}












