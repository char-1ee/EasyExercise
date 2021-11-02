package sg.edu.ntu.scse.cz2006.ontology.easyexercise.json.weather;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather.WeatherDataType;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather.WeatherForecastResult;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.utils.DateUtil;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.utils.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Processes the weather forecast information received.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class WeatherForecast extends WeatherJSONAPI {
    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";

    private final boolean apiIsHealthy;
    private final Date timestamp;
    private final Map<Coordinates, String> data;

    public WeatherForecast(String jsonString) {
        super(WeatherDataType.WEATHER_FORECAST);

        boolean apiIsHealthy;
        Date timestamp;
        Map<Coordinates, String> data = new HashMap<>();
        JSONArray locations;
        JSONArray forecasts;

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
            locations = json.getJSONArray("area_metadata");
            forecasts = json.getJSONArray("items").getJSONObject(0)
                    .getJSONArray("forecasts");
            for (int i = 0; i < locations.length(); ++i) {
                JSONObject location = locations.getJSONObject(i);
                double latitude = location.getJSONObject("label_location")
                        .getDouble("latitude");
                double longitude = location.getJSONObject("label_location")
                        .getDouble("longitude");
                String name = location.getString("name");
                String forecast = JSONUtil.findMatchString(forecasts, "area",
                        name, "forecast");
                data.put(new Coordinates(latitude, longitude, name), forecast);
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

    public WeatherForecastResult getForecast(Coordinates location) {
        return getForecast(location.getLatitude(), location.getLongitude());
    }

    public WeatherForecastResult getForecast(double latitude,
                                             double longitude) {
        Coordinates closest = null;
        Double closestDistance = Double.POSITIVE_INFINITY;
        String forecast = null;
        for (var entry : data.entrySet()) {
            Double distance = entry.getKey().getDistance(latitude, longitude);
            if (distance < closestDistance) {
                closest = entry.getKey();
                closestDistance = distance;
                forecast = entry.getValue();
            }
        }
        return new WeatherForecastResult(closest, closestDistance, forecast);
    }
}
