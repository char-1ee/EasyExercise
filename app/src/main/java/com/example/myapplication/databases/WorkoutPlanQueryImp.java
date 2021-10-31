//package com.example.myapplication.databases;
//
//import static android.provider.BaseColumns._ID;
////import static com.example.myapplication.beans.WorkoutPlan.WorkoutPlanStatus.getType;
//import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.KEY_FACILITY_ID;
//import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.KEY_SPORT_ID;
//import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.KEY_STATUS;
//import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.TABLE_NAME_WORKOUT_PLAN;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.example.myapplication.beans.Facility;
//import com.example.myapplication.beans.Location;
//import com.example.myapplication.beans.Sport;
//import com.example.myapplication.beans.WorkoutPlan;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// *
// * @author Li Xingjian
// */
//public class WorkoutPlanQueryImp {  // TODO: write a test
//
//    private WorkoutPlan workoutPlan;
//
//    public WorkoutPlanQueryImp() {
//    }
//
//    public void insert(WorkoutPlan workoutPlan) {
//        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_SPORT_ID, workoutPlan.getSport().getId());
//        int facilityId = -1;
//        if (workoutPlan.getLocation().getType() == Location.LocationType.FACILITY) {
//            facilityId = ((Facility) workoutPlan.getLocation()).getId();    // TODO: high probability only -1 can be passed
//        }
//        values.put(KEY_FACILITY_ID, facilityId);
//        values.put(KEY_STATUS, workoutPlan.getStatus().toString());
//
//        db.insert(TABLE_NAME_WORKOUT_PLAN, null, values);
//        DatabaseManager.getInstance().closeDatabase();
//    }
//
//    public void deleteAll() {
//        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
//        db.delete(TABLE_NAME_WORKOUT_PLAN, null, null);
//        DatabaseManager.getInstance().closeDatabase();
//    }
//
//    public void deleteWorkoutPlanById(int id) {
//        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
//        db.delete(TABLE_NAME_WORKOUT_PLAN, _ID + " =?", new String[]{String.valueOf(id)});
//        DatabaseManager.getInstance().closeDatabase();
//    }
//
//    public List<WorkoutPlan> getWorkoutPlanList(Context context) {
//        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM TABLE_NAME_WORKOUT_PLAN", null);
//        if (cursor != null) {
//            List<WorkoutPlan> planList = new ArrayList<>();
//            SportAndFacilityDBHelper dbHelper = new SportAndFacilityDBHelper(context);
//            dbHelper.openDatabase();
//            while (cursor.moveToNext()) {
//                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
//                int sportId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SPORT_ID));
//                int facilityId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_FACILITY_ID));
//                String status = cursor.getString(cursor.getColumnIndexOrThrow(KEY_STATUS));
//
//                Sport sport = dbHelper.getSportById(sportId);
//                Facility facility = dbHelper.getFacilityById(facilityId);
//
//                planList.add(new WorkoutPlan(sport, facility, id, getType(status)));
//            }
//            dbHelper.closeDatabase();
//            cursor.close();
//            DatabaseManager.getInstance().closeDatabase();
//            return planList;
//        } else {
//            DatabaseManager.getInstance().closeDatabase();
//            return null;
//        }
//    }
//}
