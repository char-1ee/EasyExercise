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

public abstract class RealTimeWeather extends WeatherJSONAPI {
    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";

    private final boolean apiIsHealthy;
    private final Date timestamp;
    private final Map<Coordinates, Double> data;

    public RealTimeWeather(WeatherDataType type, String jsonString) {
        super(type);

        boolean apiIsHealthy;
        Date timestamp;
        Map<Coordinates, Double> data = new HashMap<>();
        JSONArray locations;
        JSONArray readings;

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
            locations = json.getJSONObject("metadata").getJSONArray("stations");
            readings = json.getJSONArray("items").getJSONObject(0)
                    .getJSONArray("readings");
            for (int i = 0; i < locations.length(); ++i) {
                JSONObject location = locations.getJSONObject(i);
                double latitude = location.getJSONObject("location")
                        .getDouble("latitude");
                double longitude = location.getJSONObject("location")
                        .getDouble("longitude");
                String name = location.getString("name");
                String id = location.getString("id");
                Double reading = JSONUtil.findMatchDouble(readings,
                        "station_id", id, "value");
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
        return new RealTimeWeatherResult(getType(), closest, closestDistance,
                reading);
    }
}
