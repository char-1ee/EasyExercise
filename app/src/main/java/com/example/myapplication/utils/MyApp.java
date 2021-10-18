package com.example.myapplication.utils;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    public Context context = null;

    @Override
    public void onCreate() {
         super.onCreate();
         context = getApplicationContext();
     }
}
