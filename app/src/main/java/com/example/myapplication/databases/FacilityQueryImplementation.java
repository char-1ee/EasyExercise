package com.example.myapplication.databases;

import static android.provider.BaseColumns._ID;
import static com.example.myapplication.databases.DatabaseContract.FacilityTable.KEY_ADDRESS;
import static com.example.myapplication.databases.DatabaseContract.FacilityTable.KEY_DESCRIPTION;
import static com.example.myapplication.databases.DatabaseContract.FacilityTable.KEY_NAME;
import static com.example.myapplication.databases.DatabaseContract.FacilityTable.KEY_POSTAL_CODE;
import static com.example.myapplication.databases.DatabaseContract.FacilityTable.KEY_SPORTS;
import static com.example.myapplication.databases.DatabaseContract.FacilityTable.KEY_URL;
import static com.example.myapplication.databases.DatabaseContract.FacilityTable.TABLE_NAME_FACILITIES;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.beans.Facility;

import java.util.ArrayList;
import java.util.List;

public class FacilityQueryImplementation implements QueryContract.FacilityQuery {

    private DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    @Override
    public void readAllFacility(QueryResponse<List<Facility>> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Facility> facilityList = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TABLE_NAME_FACILITIES, null, null, null, null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                do {
//                    Facility facility = getFacilityFromCursor(cursor);
//                    facilityList.add(facility);
                } while (cursor.moveToNext());

                response.onSuccess(facilityList);
            } else
                response.onFailure("There are no facility in database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

//    private Facility getFacilityFromCursor(Cursor cursor) {
//        int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
//        String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
//        String url = cursor.getString(cursor.getColumnIndexOrThrow(KEY_URL));
//        String address = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ADDRESS));
//        String postalCode = cursor.getString(cursor.getColumnIndexOrThrow(KEY_POSTAL_CODE));
//        String description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION));
//        String sports = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SPORTS));

//        return new Facility(id, name, url, address, postalCode, description, sports );
//    }
}
