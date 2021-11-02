package sg.edu.ntu.scse.cz2006.ontology.easyexercise.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A class to ensure thread-safe database connections.<p>
 * Sample usage is like:
 * <pre>
 *     SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
 *     database.insert(...);
 *     DatabaseManager.getInstance().closeDatabase();
 * </pre>
 *
 * @author Li Xingjian
 */
public class DatabaseManager {
    /**
     * A counter indicates how many times database is opened.
     * If it equals one, it means we need to create a new database;
     * if not, the database is already created.
     */
    private final AtomicInteger mOpenCounter = new AtomicInteger();

    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    /**
     * Initialize SQLite database helper.
     *
     * @param helper {@link SQLiteOpenHelper} object
     */
    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    /**
     * Get database object in Singleton pattern.
     *
     * @return database instance if only one instance exists
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        } else {
            initializeInstance(mDatabaseHelper);
        }

        return instance;
    }

    /**
     * Open the SQLite database.
     *
     * @return the database opened
     */
    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    /**
     * Close the SQLite database.
     */
    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();
        }
    }
}