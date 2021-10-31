//package com.example.myapplication.databases;
//
//import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.KEY_END_TIME;
//import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.KEY_FACILITY_ID;
//import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.KEY_SPORT_ID;
//import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.KEY_START_TIME;
//import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.KEY_STATUS;
//import static com.example.myapplication.databases.DatabaseContract.WorkoutHistoryTable.TABLE_NAME_WORKOUT_HISTORY;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.example.myapplication.beans.Facility;
//import com.example.myapplication.beans.Location;
//import com.example.myapplication.beans.Sport;
//import com.example.myapplication.beans.WorkoutRecord;
//import com.example.myapplication.utils.DateUtil;
//import com.example.myapplication.utils.TimeUtil;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class WorkoutRecordQueryImp {
//
//    private WorkoutRecord workoutRecord;
//
//    public WorkoutRecordQueryImp(WorkoutRecord record) {
//        this.workoutRecord = record;
//    }
//
//    public void insert(WorkoutRecord workoutRecord) {
//        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(KEY_SPORT_ID, workoutRecord.getSport().getId());
//        int facilityId = -1;
//        if (workoutRecord.getLocation().getType() == Location.LocationType.FACILITY) {
//            facilityId = ((Facility) workoutRecord.getLocation()).getId();    // TODO: high probability only -1 can be passed
//        }
//        values.put(KEY_FACILITY_ID, facilityId);
//        values.put(KEY_STATUS, workoutRecord.getStatus().toString());
//        values.put(KEY_START_TIME, workoutRecord.getStartTime().toString());
//
//        db.insert(TABLE_NAME_WORKOUT_HISTORY, null, values);
//        DatabaseManager.getInstance().closeDatabase();
//    }
//
//    public void delete() {
//        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
//        db.delete(TABLE_NAME_WORKOUT_HISTORY, null, null);
//        DatabaseManager.getInstance().closeDatabase();
//    }
//
//    public List<WorkoutRecord> getWorkoutRecordList(Context context) {
//        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM TABLE_NAME_WORKOUT_HISTORY", null);
//        if (cursor != null) {
//            List<WorkoutRecord> recordList = new ArrayList<>();
//            SportAndFacilityDBHelper dbHelper = new SportAndFacilityDBHelper(context);
//            dbHelper.openDatabase();
//            while (cursor.moveToNext()) {
//                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
//                int sportId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SPORT_ID));
//                int facilityId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_FACILITY_ID));
//                String startTimeText = cursor.getString(cursor.getColumnIndexOrThrow(KEY_START_TIME));
//                String endTimeText = cursor.getString(cursor.getColumnIndexOrThrow(KEY_END_TIME));
//
//                Sport sport = dbHelper.getSportById(sportId);
//                Facility facility = dbHelper.getFacilityById(facilityId);
//
//                Date startTime = DateUtil.getDateFromString(startTimeText, TimeUtil.pattern2);
//                Date endTime = DateUtil.getDateFromString(endTimeText, TimeUtil.pattern2);
//
//                recordList.add(new WorkoutRecord(sport, facility, id, startTime, endTime));
//            }
//            dbHelper.closeDatabase();
//            cursor.close();
//            DatabaseManager.getInstance().closeDatabase();
//            return recordList;
//        } else {
//            DatabaseManager.getInstance().closeDatabase();
//            return null;
//        }
//    }
//}
