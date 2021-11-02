package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.beans;

public class WeatherData {
    private float temperature;
    private float humidity;
    private float uvIndex;
    private String precipitation;

    public WeatherData(float temperature, float humidity, float uvIndex, String precipitation) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.uvIndex = uvIndex;
        this.precipitation = precipitation;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(float uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }
}
