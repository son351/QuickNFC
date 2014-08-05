package vn.edu.hcmus.ltdd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by mac on 8/6/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    @JsonProperty("date")
    private String date;
    @JsonProperty("precipMM")
    private String precipMM;
    @JsonProperty("tempMaxC")
    private String tempMaxC;
    @JsonProperty("tempMaxF")
    private String tempMaxF;
    @JsonProperty("tempMinC")
    private String tempMinC;
    @JsonProperty("tempMinF")
    private String tempMinF;
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
    @JsonProperty("winddirection")
    private String winddirection;
    @JsonProperty("windspeedKmph")
    private String windspeedKmph;
    @JsonProperty("windspeedMiles")
    private String windspeedMiles;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrecipMM() {
        return precipMM;
    }

    public void setPrecipMM(String precipMM) {
        this.precipMM = precipMM;
    }

    public String getTempMaxC() {
        return tempMaxC;
    }

    public void setTempMaxC(String tempMaxC) {
        this.tempMaxC = tempMaxC;
    }

    public String getTempMaxF() {
        return tempMaxF;
    }

    public void setTempMaxF(String tempMaxF) {
        this.tempMaxF = tempMaxF;
    }

    public String getTempMinC() {
        return tempMinC;
    }

    public void setTempMinC(String tempMinC) {
        this.tempMinC = tempMinC;
    }

    public String getTempMinF() {
        return tempMinF;
    }

    public void setTempMinF(String tempMinF) {
        this.tempMinF = tempMinF;
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

    public String getWinddirection() {
        return winddirection;
    }

    public void setWinddirection(String winddirection) {
        this.winddirection = winddirection;
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
}
