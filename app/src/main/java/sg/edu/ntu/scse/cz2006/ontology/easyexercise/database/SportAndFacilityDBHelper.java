package sg.edu.ntu.scse.cz2006.ontology.easyexercise.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;

/**
 * Helper class for {@link Sport} and {@link Facility} databases.<p>
 * Sample usage is like:
 * <pre>
 *     SportAndFacilityDBHelper dbHelper = new SportAndFacilityDBHelper(this);
 *     dbHelper.openDatabase();
 *     // Your code here
 *     dbHelper.closeDatabase();
 * </pre>
 *
 * @author Li Xingjian
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class SportAndFacilityDBHelper {
    private static final int BUFFER_SIZE = 400000;
    private static final String DB_NAME = "sports.db";
    private static final String PACKAGE_NAME = "sg.edu.ntu.scse.cz2006.ontology.easyexercise";
    private static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;

    private SQLiteDatabase database;
    private final Context context;

    /**
     * Constructor for class {@code SportAndFacilityDBHelper}.
     *
     * @param context current activity {@link Context}
     */
    public SportAndFacilityDBHelper(Context context) {
        this.context = context;
    }

    /**
     * Assign {@code database} by call {@code openDatabase(String)}.
     *
     * @see SportAndFacilityDBHelper#openDatabase(String)
     */
    public void openDatabase() {
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }

    /**
     * Open SQLite database in {@code DB_PATH}.
     *
     * @param dbFile database file path in {@link String}
     * @return the SQLiteDatabase in path, {@code null} if not exists
     */
    private SQLiteDatabase openDatabase(String dbFile) {
        try {
            InputStream is = this.context.getResources().openRawResource(R.raw.sports);
            FileOutputStream fos = new FileOutputStream(dbFile);
            byte[] buffer = new byte[BUFFER_SIZE];
            int count;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            is.close();
            return SQLiteDatabase.openOrCreateDatabase(dbFile, null);
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all data of sport in a list.
     *
     * @return list of {@link Sport}, {@code null} if {@code cursor} pointer null
     */
    public List<Sport> getSports() {
        Cursor cursor = database.rawQuery("SELECT * FROM sports", null);
        if (cursor != null) {
            List<Sport> sportList = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String alternativeName =
                        cursor.getString(cursor.getColumnIndexOrThrow("alternative_name"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                Sport sport = new Sport(id, name, alternativeName, Sport.SportType.getType(type));
                sportList.add(sport);
            }
            cursor.close();
            return sportList;
        } else {
            return null;
        }
    }

    /**
     * Get sport data by {@code sportId}.
     *
     * @param sportId targeted sport Id
     * @return {@link Sport} instance, otherwise {@code null} if no matches
     */
    public Sport getSportById(long sportId) {
        @SuppressLint("DefaultLocale")
        Cursor cursor =
                database.rawQuery(String.format("SELECT * FROM sports WHERE _id = %d", sportId), null);
        if (cursor != null) {
            Sport sport = null;
            if (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String alternativeName =
                        cursor.getString(cursor.getColumnIndexOrThrow("alternative_name"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                sport = new Sport(id, name, alternativeName, Sport.SportType.getType(type));
            }
            cursor.close();
            return sport;
        } else {
            return null;
        }
    }

    /**
     * Get all data of facility in a list.
     *
     * @return list of {@link Facility}, {@code null} if {@code cursor} pointer null
     */
    public List<Facility> getFacilities() {
        Cursor cursor = database.rawQuery("SELECT * FROM facilities", null);
        if (cursor != null) {
            List<Sport> sportList = getSports();
            List<Facility> facilityList = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String url = cursor.getString(cursor.getColumnIndexOrThrow("url"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String postalCode = cursor.getString(cursor.getColumnIndexOrThrow("postal_code"));
                String description =
                        cursor.getString(cursor.getColumnIndexOrThrow("description"))
                                .replace(";", "\n");
                double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
                double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
                List<Long> sportIDs = Stream.of(
                        cursor.getString(cursor.getColumnIndexOrThrow("sports")).split(",")
                ).map(Long::parseLong).collect(Collectors.toList());
                Facility facility = new Facility(id, name, url, address, postalCode, description, latitude, longitude);
                sportList.stream().filter(sport -> sportIDs.contains(sport.getId())).forEach(facility::addSport);
                facilityList.add(facility);
            }
            cursor.close();
            return facilityList;
        } else {
            return null;
        }
    }

    /**
     * Get sport data by {@code sportId}.
     *
     * @param facilityId targeted facility Id
     * @return {@link Facility} instance, otherwise {@code null} if no matches
     */
    public Facility getFacilityById(long facilityId) {
        @SuppressLint("DefaultLocale")
        Cursor cursor = database.rawQuery(String.format("SELECT * FROM facilities WHERE _id = %d", facilityId), null);
        if (cursor != null) {
            Facility facility = null;
            if (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String url = cursor.getString(cursor.getColumnIndexOrThrow("url"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String postalCode = cursor.getString(cursor.getColumnIndexOrThrow("postal_code"));
                String description =
                        cursor.getString(cursor.getColumnIndexOrThrow("description"))
                                .replace(";", "\n");
                double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
                double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
                facility = new Facility(id, name, url, address, postalCode, description, latitude, longitude);
            }
            cursor.close();
            return facility;
        } else {
            return null;
        }
    }

    /**
     * Close current SQLite database.
     */
    public void closeDatabase() {
        this.database.close();
    }
}
