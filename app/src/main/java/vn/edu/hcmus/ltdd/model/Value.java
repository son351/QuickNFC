package vn.edu.hcmus.ltdd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mac on 8/5/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {
    @JsonProperty("value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
