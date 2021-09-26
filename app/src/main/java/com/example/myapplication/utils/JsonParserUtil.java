package com.example.myapplication.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is a demo JsonParser class which can be inherited for different types of data parsers.
 */
public class JsonParserUtil {

    private Context mContext;

    public JsonParserUtil(Context context) { mContext = context; }

    /**
     * Parse Json data and store as a string
     */
    public void jsonDataToString() {
        InputStream inputStreamReader = null;   // InputStream object must be initialized
        String jsonData = "";
        try {
            // TODO: uncomment below after adding an assets folder under com.example.myapplication and storing .json files in it.
//            inputStreamReader = getAssets().open("fileData.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamReader));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            bufferedReader.close();
            jsonData = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initData(jsonData);
    }

    /**
     * Initiate data in type String (for example) for further operations.
     * @param result data in String
     */
    public void initData(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0) {
                //TODO the part operates on parsed data
            } else {
                ToastUtil.ToastSize(mContext.getApplicationContext(), "Currently lack of data.", 25);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
