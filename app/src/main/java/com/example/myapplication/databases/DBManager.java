package com.example.myapplication.databases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.beans.Sport;

public class DBManager {
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "data.db";
    public static final String PACKAGE_NAME = "com.example.myapplication";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;

    private SQLiteDatabase database;
    private Context context;

    DBManager(Context context) {
        this.context = context;
    }

    public void openDatabase() {
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }

    private SQLiteDatabase openDatabase(String dbFile) {
        try {
            if (!(new File(dbFile).exists())){
                InputStream is = this.context.getResources().openRawResource(R.raw.data);
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            return SQLiteDatabase.openOrCreateDatabase(dbFile,null);
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Sport> getIndoorSport() {
        Cursor cursor = database.rawQuery("SELECT name FROM sports WHERE type = 'INDOOR'", null);
        if (cursor != null) {
            int NUM = cursor.getCount();
            ArrayList<Sport> sportList = new ArrayList<Sport>(NUM + 1);
            if (cursor.moveToNext()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String alternativeName = cursor.getString(cursor.getColumnIndexOrThrow("alternative_name"));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                    Sport sport = new Sport(id, name, alternativeName, Sport.SportType.getType(type));
                    sportList.add(sport);
                } while (cursor.moveToNext());
            }
            return sportList;
        } else {
            return null;
        }
    }


    public void closeDatabase() {
        this.database.close();
    }
}


/*
    public DBManager db = new DBManager(this);
    db.openDatabase();
    //
    db.closeDatabase();

 */