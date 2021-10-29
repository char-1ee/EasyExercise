package com.example.myapplication.json.weather;

import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.weather.RealTimeWeatherResult;
import com.example.myapplication.beans.weather.WeatherDataType;
import com.example.myapplication.utils.DateUtil;
import com.example.myapplication.utils.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Processes the PM2.5 information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class PM25 extends WeatherJSONAPI {
    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";

    private final boolean apiIsHealthy;
    private final Date timestamp;
    private final Map<Coordinates, Double> data;

    public PM25(String jsonString) {
        super(WeatherDataType.PM25);

        boolean apiIsHealthy;
        Date timestamp;
        Map<Coordinates, Double> data = new HashMap<>();
        JSONArray locations;
        JSONObject readings;

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
            locations = json.getJSONArray("region_metadata");
            readings = json.getJSONArray("items").getJSONObject(0)
                    .getJSONObject("readings").getJSONObject("pm25_one_hourly");
            for (int i = 0; i < locations.length(); ++i) {
                JSONObject location = locations.getJSONObject(i);
                double latitude = location.getJSONObject("label_location")
                        .getDouble("latitude");
                double longitude = location.getJSONObject("label_location")
                        .getDouble("longitude");
                String name = location.getString("name");
                Double reading = readings.getDouble(name);
                data.put(new Coordinates(latitude, longitude, name), reading);
            }
        } catch (JSONException e) {
            apiIsHealthy = false;
            timestamp = null;
            data = null;
        }

        this.apiIsHealthy = apiIsHealthy;
        this.timestamp = timestamp;
        this.data = data;
    }

    @Override
    public boolean apiIsHealthy() {
        return apiIsHealthy;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public RealTimeWeatherResult getReading(Coordinates location) {
        return getReading(location.getLatitude(), location.getLongitude());
    }

    public RealTimeWeatherResult getReading(double latitude, double longitude) {
        Coordinates closest = null;
        Double closestDistance = Double.POSITIVE_INFINITY;
        Double reading = null;
        for (var entry : data.entrySet()) {
            Double distance = entry.getKey().getDistance(latitude, longitude);
            if (distance < closestDistance) {
                closest = entry.getKey();
                closestDistance = distance;
                reading = entry.getValue();
            }
        }
        return new RealTimeWeatherResult(WeatherDataType.PM25, closest,
                closestDistance, reading);
    }
}
