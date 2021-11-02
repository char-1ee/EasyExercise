package sg.edu.ntu.scse.cz2006.ontology.easyexercise.database;

import android.provider.BaseColumns;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.PrivateWorkoutPlan;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.WorkoutRecord;

/**
 * Contract for the application's local database.
 *
 * @author Li Xingjian
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public final class DatabaseContract {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "sports.db";
    // List of all SQL create table statements
    public static final String[] SQL_CREATE_TABLE_LIST = {
            SportTable.CREATE_TABLE_SPORT,
            FacilityTable.CREATE_TABLE_FACILITY,
            WorkoutPlanTable.CREATE_TABLE_WORKOUT_PLAN,
            WorkoutHistoryTable.CREATE_TABLE_WORKOUT_HISTORY
    };
    private static final String INTEGER_TYPE = " INTEGER ";
    private static final String DOUBLE_TYPE = " DOUBLE ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String TEXT_TYPE = " TEXT ";
    private static final String COMMA_SEP = " , ";

    /**
     * Private constructor.
     */
    private DatabaseContract() {
        // Not allowed to be instantiated
    }

    /**
     * Inner class contains constants of {@link Sport} table name, column names, SQLite queries.
     */
    public static abstract class SportTable implements BaseColumns {
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
         /*
            CREATE TABLE sports (
             _id INTEGER PRIMARY KEY,
             name TEXT,
             alternative_name TEXT,
             type TEXT CHECK ( type IN ( 'Indoor', 'Outdoor', 'Indoor/Outdoor' )
            )
        */

        public static final String DELETE_TABLE_SPORTS = " DROP TABLE IF EXISTS " + TABLE_NAME_SPORTS;
    }

    /**
     * Inner class contains constants of {@link Facility} table name, column names, SQLite queries.
     */
    public static abstract class FacilityTable implements BaseColumns {
        public static final String TABLE_NAME_FACILITIES = " facilities ";
        public static final String KEY_NAME = " name ";
        public static final String KEY_URL = " url ";
        public static final String KEY_ADDRESS = " address ";
        public static final String KEY_POSTAL_CODE = " postal_code ";
        public static final String KEY_DESCRIPTION = " description ";
        public static final String KEY_LATITUDE = " latitude ";
        public static final String KEY_LONGITUDE = " longitude ";
        public static final String KEY_SPORTS = " sportId ";

        public static final String CREATE_TABLE_FACILITY = " CREATE TABLE " + TABLE_NAME_FACILITIES + " ( " +
                _ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                KEY_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_URL + TEXT_TYPE + COMMA_SEP +
                KEY_ADDRESS + TEXT_TYPE + COMMA_SEP +
                KEY_POSTAL_CODE + TEXT_TYPE + COMMA_SEP +
                KEY_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                KEY_LATITUDE + DOUBLE_TYPE + COMMA_SEP +
                KEY_LONGITUDE + DOUBLE_TYPE + COMMA_SEP +
                KEY_SPORTS + TEXT_TYPE +
                " ) ";
         /*
            CREATE TABLE facilities (
             _id INTEGER PRIMARY KEY,
             name TEXT,
             url TEXT,
             address TEXT,
             postal_code TEXT,
             description TEXT,
             latitude DOUBLE,
             longitude DOUBLE,
             sports TEXT
            );
         */

        public static final String DELETE_TABLE_FACILITIES = "DROP TABLE IF EXISTS " + TABLE_NAME_FACILITIES;
    }

    /**
     * Inner class contains constants of {@link PrivateWorkoutPlan} table name, column names, SQLite queries.
     */
    public static abstract class WorkoutPlanTable implements BaseColumns {
        public static final String TABLE_NAME_WORKOUT_PLAN = "WorkoutPlanTable";
        public static final String KEY_SPORT_ID = "sportId";
        public static final String KEY_FACILITY_ID = "facilityId";
        public static final String KEY_STATUS = "workoutPlanStatus";

        /*
           CREATE TABLE workoutPlanTable (
            _id INTEGER PRIMARY KEY,
            sportId INTEGER,
            facilityId INTEGER,
            FOREIGN KEY (sportId) REFERENCES sports (id)
            FOREIGN KEY (facilityId) REFERENCES facilities (id)
           )
         */
        public static final String CREATE_TABLE_WORKOUT_PLAN = "CREATE TABLE " +
                TABLE_NAME_WORKOUT_PLAN + "(" +
                _ID + "INTEGER PRIMARY KEY" +
                KEY_SPORT_ID + INTEGER_TYPE + COMMA_SEP +
                KEY_FACILITY_ID + INTEGER_TYPE + COMMA_SEP +
                KEY_STATUS + TEXT_TYPE + COMMA_SEP +
                "FOREIGN KEY (" + KEY_SPORT_ID + ") REFERENCES " + SportTable.TABLE_NAME_SPORTS + "(id)" +
                "FOREIGN KEY (" + KEY_FACILITY_ID + ") REFERENCES " + FacilityTable.TABLE_NAME_FACILITIES + "(id)" + ")";

        public static final String DELETE_TABLE_WORKOUT_PLAN = "DROP TABLE IF EXISTS " + TABLE_NAME_WORKOUT_PLAN;
    }

    /**
     * Inner class contains constants of {@link WorkoutRecord} table name, column names, SQLite queries.
     */
    public static abstract class WorkoutHistoryTable implements BaseColumns {
        public static final String TABLE_NAME_WORKOUT_HISTORY = "WorkoutHistoryTable";
        public static final String KEY_SPORT_ID = "sportId";
        public static final String KEY_FACILITY_ID = "facilityId";
        public static final String KEY_STATUS = "workoutPlanStatus";
        public static final String KEY_START_TIME = "startTime";
        public static final String KEY_END_TIME = "endTime";

        /*
           CREATE TABLE workoutHistoryTable (
            _id INTEGER PRIMARY KEY,
            sportId INTEGER,
            facilityId INTEGER,
            startTime TEXT,
            endTime TEXT,
            FOREIGN KEY (sportId) REFERENCES sports (id)
            FOREIGN KEY (facilityId) REFERENCES facilities (id)
           )
         */
        public static final String CREATE_TABLE_WORKOUT_HISTORY = "CREATE TABLE " +
                TABLE_NAME_WORKOUT_HISTORY + "(" +
                _ID + "INTEGER PRIMARY KEY" +
                KEY_SPORT_ID + INTEGER_TYPE + COMMA_SEP +
                KEY_FACILITY_ID + INTEGER_TYPE + COMMA_SEP +
                KEY_STATUS + TEXT_TYPE + COMMA_SEP +
                KEY_START_TIME + TEXT_TYPE + COMMA_SEP +
                KEY_END_TIME + TEXT_TYPE + COMMA_SEP +
                "FOREIGN KEY (" + KEY_SPORT_ID + ") REFERENCES " + SportTable.TABLE_NAME_SPORTS + "(id)" +
                "FOREIGN KEY (" + KEY_FACILITY_ID + ") REFERENCES " + FacilityTable.TABLE_NAME_FACILITIES + "(id)" + ")";

        public static final String DELETE_TABLE_WORKOUT_HISTORY = "DROP TABLE IF EXISTS " + TABLE_NAME_WORKOUT_HISTORY;
    }
}












