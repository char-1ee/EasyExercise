package com.example.myapplication.json.weather;

import com.example.myapplication.beans.weather.WeatherDataType;
import com.example.myapplication.utils.DateUtil;
import com.example.myapplication.utils.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Processes the UV index information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class UVIndex extends WeatherJSONAPI {
    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";

    private final boolean apiIsHealthy;
    private final Date timestamp;
    private final Integer value;

    public UVIndex(String jsonString) {
        super(WeatherDataType.UV_INDEX);

        boolean apiIsHealthy;
        Date timestamp;
        Integer value;

        JSONObject json = JSONUtil.getJSONObjectFromString(jsonString);
        try {
            String apiStatus =
                    json.getJSONObject("api_info").getString("status");
            if (!apiStatus.equals("healthy")) {
                throw new JSONException(
                        String.format("Unhealthy API status: %s", apiStatus));
            }
            apiIsHealthy = true;
            timestamp = DateUtil.getDateFromString(json.getJSONArray("items")
                    .getJSONObject(0).getString("timestamp"), DATE_PATTERN);
            value = json.getJSONArray("items").getJSONObject(0)
                    .getJSONArray("index").getJSONObject(0).getInt("value");
        } catch (JSONException e) {
            apiIsHealthy = false;
            timestamp = null;
            value = null;
        }

        this.apiIsHealthy = apiIsHealthy;
        this.timestamp = timestamp;
        this.value = value;
    }

    @Override
    public boolean apiIsHealthy() {
        return apiIsHealthy;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Integer getValue() {
        return value;
    }
}
