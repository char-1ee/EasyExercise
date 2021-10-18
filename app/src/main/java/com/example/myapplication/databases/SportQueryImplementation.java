package com.example.myapplication.databases;

import static android.provider.BaseColumns._ID;
import static com.example.myapplication.databases.DatabaseContract.SportTable.KEY_ALTERNATIVE_NAME;
import static com.example.myapplication.databases.DatabaseContract.SportTable.KEY_NAME;
import static com.example.myapplication.databases.DatabaseContract.SportTable.KEY_SPORT_TYPE;
import static com.example.myapplication.databases.DatabaseContract.SportTable.TABLE_NAME_SPORTS;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.beans.Sport;

import java.util.ArrayList;
import java.util.List;

public class SportQueryImplementation implements QueryContract.SportQuery {

    private DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    @Override
    public void readAllSport(QueryResponse<List<Sport>> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Sport> sportList = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TABLE_NAME_SPORTS, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Sport sport = getSportFromCursor(cursor);
                    sportList.add(sport);
                } while (cursor.moveToNext());

                response.onSuccess(sportList);
            } else
                response.onFailure("There are no sport in database");

        } catch (Exception e) {
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if (cursor != null)
                cursor.close();
        }
    }

    private Sport getSportFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
        String alternativeName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ALTERNATIVE_NAME));
        String type = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SPORT_TYPE));

        return new Sport(id, name, alternativeName, type);
    }

}
