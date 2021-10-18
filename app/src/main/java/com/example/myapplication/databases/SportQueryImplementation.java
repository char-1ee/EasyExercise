package com.example.myapplication.databases;

import static android.provider.BaseColumns._ID;
import static com.example.myapplication.databases.DatabaseContract.Sport.KEY_ALTERNATIVE_NAME;
import static com.example.myapplication.databases.DatabaseContract.Sport.KEY_NAME;
import static com.example.myapplication.databases.DatabaseContract.Sport.KEY_SPORT_TYPE;
import static com.example.myapplication.databases.DatabaseContract.Sport.TABLE_NAME_SPORTS;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.myapplication.beans.Sport;

import java.util.ArrayList;
import java.util.List;

public class SportQueryImplementation implements QueryContract.SportQuery{

    private DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    @Override
    public void readAllSport(QueryResponse<Sport> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Sport> sportList = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TABLE_NAME_SPORTS, null, null, null, null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Sport sport = getSportFromCursor(cursor);
                    sportList.add(sport);
                } while (cursor.moveToNext());

                response.onSuccess((Sport) sportList);
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

    private Sport getSportFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(_ID));
        String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
        String alternativeName = cursor.getString(cursor.getColumnIndex(KEY_ALTERNATIVE_NAME));
        String type = cursor.getString(cursor.getColumnIndex(KEY_SPORT_TYPE));

        return new Sport(id, name, alternativeName, type);
    }

}
