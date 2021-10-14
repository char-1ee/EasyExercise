package com.example.myapplication.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class IOUtil {
    private static final String lineSeparator =
            System.getProperty("line.separator");

    public String readFromURL(String urlString) {
        try {
            URL url = new URL(urlString);
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder stringBuilder = new StringBuilder();
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                stringBuilder.append(line);
                stringBuilder.append(lineSeparator);
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
