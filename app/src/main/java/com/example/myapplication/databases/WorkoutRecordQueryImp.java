package com.example.myapplication.databases;

import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.KEY_END_TIME;
import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.KEY_FACILITY_ID;
import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.KEY_SPORT_ID;
import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.KEY_START_TIME;
import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.KEY_STATUS;
import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.TABLE_NAME_WORKOUT_HISTORY;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutRecord;
import com.example.myapplication.utils.DateUtil;
import com.example.myapplication.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutRecordQueryImp {

    private WorkoutRecord workoutRecord;

    public WorkoutRecordQueryImp() {
        this.workoutRecord = new WorkoutRecord();
    }

    public void insert(WorkoutRecord workoutRecord) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SPORT_ID, workoutRecord.getSport().getId());
        values.put(KEY_FACILITY_ID, workoutRecord.getFacility().getId());
        values.put(KEY_STATUS, workoutRecord.getStatus().toString());
        values.put(KEY_START_TIME, workoutRecord.getStartTime().toString());

        db.insert(TABLE_NAME_WORKOUT_HISTORY, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(TABLE_NAME_WORKOUT_HISTORY, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<WorkoutRecord> getWorkoutRecordList(Context context) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TABLE_NAME_WORKOUT_HISTORY", null);
        if (cursor != null) {
            List<WorkoutRecord> recordList = new ArrayList<>();
            if (cursor.getCount()>0) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                    int sportId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SPORT_ID));
                    int facilityId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_FACILITY_ID));
                    String startTimeText = cursor.getString(cursor.getColumnIndexOrThrow(KEY_START_TIME));
                    String endTimeText = cursor.getString(cursor.getColumnIndexOrThrow(KEY_END_TIME));

                    Sport sport = new Sport();
                    Facility facility = new Facility();
                    cursorMatchById(context, sport, facility, sportId, facilityId);

                    Date startTime = DateUtil.getDateFromString(startTimeText, TimeUtil.pattern2);
                    Date endTime = DateUtil.getDateFromString(endTimeText, TimeUtil.pattern2);

                    WorkoutRecord record = new WorkoutRecord(sport, facility, id, startTime, endTime);
                    recordList.add(record);
                } while (cursor.moveToNext());
            }
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            return recordList;
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
