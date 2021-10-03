package com.example.myapplication.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {
    private JSONUtil() {
    }

    public static JSONArray getJSONArrayFromString(String jsonString) {
        try {
            return new JSONArray(jsonString);
        } catch (JSONException e) {
            return null;
        }
    }

    public static JSONObject getJSONObjectFromString(String jsonString) {
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            return null;
        }
    }

    public static String findMatchString(JSONArray jsonArray,
                                         String conditionKey, String conditionValue, String targetKey) {
        try {
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject object = jsonArray.getJSONObject(i);
                if (object.getString(conditionKey).equals(conditionValue)) {
                    return object.getString(targetKey);
                }
            }
        } catch (JSONException e) {
            return null;
        }
        return null;
    }

    public static Double findMatchDouble(JSONArray jsonArray,
                                         String conditionKey, String conditionValue, String targetKey) {
        try {
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject object = jsonArray.getJSONObject(i);
                if (object.getString(conditionKey).equals(conditionValue)) {
                    return object.getDouble(targetKey);
                }
            }
        } catch (JSONException e) {
            return null;
        }
        return null;
    }
}
