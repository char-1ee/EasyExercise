package com.example.myapplication.utils;

import android.app.Application;
import android.content.Context;

/**
 *  Utility class to get application context statically.
 *
 * @author Li Xingjian
 * @see com.example.myapplication.databases.DatabaseHelper
 */
public class MyApp extends Application {
    public static Context context = null;

    @Override
    public void onCreate() {
         super.onCreate();
         context = getApplicationContext();
     }
}
