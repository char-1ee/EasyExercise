package sg.edu.ntu.scse.cz2006.ontology.easyexercise.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON utilities.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class JSONUtil {
    private JSONUtil() {
    }

    /**
     * Converts a JSON string to a JSON array.
     *
     * @param jsonString the JSON string to convert
     * @return the resulting JSON array
     */
    public static JSONArray getJSONArrayFromString(String jsonString) {
        try {
            return new JSONArray(jsonString);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Converts a JSON string to a JSON object.
     *
     * @param jsonString the JSON string to convert
     * @return the resulting JSON object
     */
    public static JSONObject getJSONObjectFromString(String jsonString) {
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Examines a JSON array. If an object in {@code JSONArray} has the key
     * {@code conditionKey} with value {@code conditionValue}, then the string
     * value in that object corresponding to key {@code targetKey} is returned.
     *
     * @param jsonArray      the JSON array to examine
     * @param conditionKey   the condition key
     * @param conditionValue the condition value
     * @param targetKey      the key to the target to look for
     * @return the result, if it exists; {@code null} otherwise
     */
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

    /**
     * Examines a JSON array. If an object in {@code JSONArray} has the key
     * {@code conditionKey} with value {@code conditionValue}, then the double
     * value in that object corresponding to key {@code targetKey} is returned.
     *
     * @param jsonArray      the JSON array to examine
     * @param conditionKey   the condition key
     * @param conditionValue the condition value
     * @param targetKey      the key to the target to look for
     * @return the result, if it exists; {@code null} otherwise
     */
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
