package sg.edu.ntu.scse.cz2006.ontology.easyexercise.utils;

import android.app.Application;
import android.content.Context;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.databases.PlanAndRecordDBHelper;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.databases.DatabaseManager;

/**
 * Utility class for singleton database.<p>
 *
 * This class will be the first file to be execute when the application running and it’s only execute once,
 * the purpose of this file is to keep variable that we need to share across all the packages and these variables are mean to create one time
 * and only has 1 instance in entire application life cycle.
 *
 * @author Li Xingjian
 * @see PlanAndRecordDBHelper
 */
public class App extends Application {
    private static Context context = null;
    private static PlanAndRecordDBHelper dbHelper;

    @Override
    public void onCreate() {
         super.onCreate();
         context = this.getApplicationContext();
         dbHelper = new PlanAndRecordDBHelper();
         DatabaseManager.initializeInstance(dbHelper);
     }

     public static Context getContext() {
        return context;
     }
}
