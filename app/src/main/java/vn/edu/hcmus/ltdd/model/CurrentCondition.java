package vn.edu.hcmus.ltdd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by mac on 8/5/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentCondition {
    @JsonProperty("cloudcover")
    private String cloudcover;
    @JsonProperty("humidity")
    private String humidity;
    @JsonProperty("observation_time")
    private String observation_time;
    @JsonProperty("precipMM")
    private String precipMM;
    @JsonProperty("pressure")
    private String pressure;
    @JsonProperty("temp_C")
    private String temp_C;
    @JsonProperty("temp_F")
    private String temp_F;
    @JsonProperty("visibility")
    private String visibility;
    @JsonProperty("weatherCode")
    private String weatherCode;
    @JsonProperty("weatherDesc")
    private List<Value> weatherDesc;
    @JsonProperty("weatherIconUrl")
    private List<Value> weatherIconUrl;
    @JsonProperty("winddir16Point")
    private String winddir16Point;
    @JsonProperty("winddirDegree")
    private String winddirDegree;
    @JsonProperty("windspeedKmph")
    private String windspeedKmph;
    @JsonProperty("windspeedMiles")
    private String windspeedMiles;
    @JsonProperty("request")
    private List<Request> request;
    @JsonProperty("weather")
    private List<Weather> weather;

    public String getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(String cloudcover) {
        this.cloudcover = cloudcover;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getObservation_time() {
        return observation_time;
    }

    public void setObservation_time(String observation_time) {
        this.observation_time = observation_time;
    }

    public String getPrecipMM() {
        return precipMM;
    }

    public void setPrecipMM(String precipMM) {
        this.precipMM = precipMM;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemp_C() {
        return temp_C;
    }

    public void setTemp_C(String temp_C) {
        this.temp_C = temp_C;
    }

    public String getTemp_F() {
        return temp_F;
    }

    public void setTemp_F(String temp_F) {
        this.temp_F = temp_F;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public List<Value> getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(List<Value> weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public List<Value> getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(List<Value> weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public String getWinddir16Point() {
        return winddir16Point;
    }

    public void setWinddir16Point(String winddir16Point) {
        this.winddir16Point = winddir16Point;
    }

    public String getWinddirDegree() {
        return winddirDegree;
    }

    public void setWinddirDegree(String winddirDegree) {
        this.winddirDegree = winddirDegree;
    }

    public String getWindspeedKmph() {
        return windspeedKmph;
    }

    public void setWindspeedKmph(String windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

    public String getWindspeedMiles() {
        return windspeedMiles;
    }

    public void setWindspeedMiles(String windspeedMiles) {
        this.windspeedMiles = windspeedMiles;
    }

    public List<Request> getRequest() {
        return request;
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
