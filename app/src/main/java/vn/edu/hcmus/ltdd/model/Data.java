package vn.edu.hcmus.ltdd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by mac on 8/6/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    @JsonProperty("current_condition")
    private List<CurrentCondition> current_condition;
    @JsonProperty("request")
    private List<Request> request;
    @JsonProperty("weather")
    private List<Weather> weather;

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

    public List<CurrentCondition> getCurrent_condition() {
        return current_condition;
    }

    public void setCurrent_condition(List<CurrentCondition> current_condition) {
        this.current_condition = current_condition;
    }
}
