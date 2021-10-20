package com.example.myapplication.databases;

import static android.provider.BaseColumns._ID;
import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.KEY_FACILITY_ID;
import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.KEY_SPORT_ID;
import static com.example.myapplication.databases.DatabaseContract.WorkoutPlanTable.TABLE_NAME_WORKOUT_PLAN;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.beans.WorkoutPlan;
import com.example.myapplication.beans.WorkoutRecord;

public class WorkoutPlanQueryImplementation {

}private DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    @Override
    public long createWorkoutPlan(WorkoutPlan workoutPlan, Sport sport, Location location) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SPORT_ID, workoutPlan.getSport());

        try {
            long id = sqLiteDatabase.insertOrThrow(TABLE_NAME_WORKOUT_PLAN, null, contentValues);
            if(id > 0) {
                response.onSuccess(true);
            }
            else
                response.onFailure("Failed to create student. Unknown Reason!");
        } catch (SQLiteException e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    @Override
    public void readWorkoutPlan(int workoutRecordId, QueryResponse<WorkoutRecord> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TABLE_NAME_WORKOUT_PLAN, null,
                    _ID + " =? ", new String[]{String.valueOf(studentId)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()) {
                Student student = getStudentFromCursor(cursor);
                response.onSuccess(student);
            }
            else
                response.onFailure("Student not found with this ID in database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void readAllStudent(QueryResponse<List<Student>> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Student> studentList = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TABLE_STUDENT, null, null, null, null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Student student = getStudentFromCursor(cursor);
                    studentList.add(student);
                } while (cursor.moveToNext());

                response.onSuccess(studentList);
            } else
                response.onFailure("There are no student in database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void updateStudent(Student student, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = getContentValuesForStudent(student);

        try {
            long rowCount = sqLiteDatabase.update(TABLE_STUDENT, contentValues,
                    STUDENT_ID + " =? ", new String[]{String.valueOf(student.getId())});
            if(rowCount>0)
                response.onSuccess(true);
            else
                response.onFailure("No data is updated at all");
        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    @Override
    public void deleteStudent(int studentId, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            long rowCount = sqLiteDatabase.delete(TABLE_NAME_WORKOUT_PLAN, STUDENT_ID + " =? ",
                    new String[]{String.valueOf(studentId)});

            if(rowCount>0)
                response.onSuccess(true);
            else
                response.onFailure("Failed to delete student. Unknown reason");
        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }


    private WorkoutPlan getStudentFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
        String sport = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SPORT_ID));
        String location = cursor.getString(cursor.getColumnIndexOrThrow(KEY_FACILITY_ID));

        return new WorkoutPlan(sport, location);
    }
}
