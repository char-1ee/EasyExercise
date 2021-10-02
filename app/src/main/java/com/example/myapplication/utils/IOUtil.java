package com.example.myapplication.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtil {
    private final Context mContext;

    public IOUtil(Context context) {
        this.mContext = context;
    }

    public String convertFileToString(String path) {
        try {
            InputStream inputStream = mContext.getAssets().open(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
