package vn.edu.hcmus.ltdd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mac on 8/6/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Final {
    @JsonProperty("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
