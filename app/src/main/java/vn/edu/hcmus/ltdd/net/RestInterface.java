package vn.edu.hcmus.ltdd.net;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import vn.edu.hcmus.ltdd.model.Final;

/**
 * Created by mac on 8/5/14.
 */
@Rest(rootUrl = "https://api.worldweatheronline.com/free/v1", converters = {MappingJackson2HttpMessageConverter.class})
public interface RestInterface extends RestClientErrorHandling {
    RestTemplate getRestTemplate();

    @Get("/weather.ashx?q=Vietnam&format=json&num_of_days=3&key=7e753965d2338851438a4edf6df809ac35d1a898")
    Final getWeather();
}
