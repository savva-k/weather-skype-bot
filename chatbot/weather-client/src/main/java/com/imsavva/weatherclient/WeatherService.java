package com.imsavva.weatherclient;

import com.imsavva.weatherclient.beans.DailyForecast;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

/**
 * @author Savva Kodeikin
 */
@Component
public class WeatherService {

    /**
     * The value should look like http://domain/some/path/{0}/{1}
     * {0} will be changed to location
     * {1} will be changed to period of time (like "today" or "week")
     */
    private static final String SERVICE_URL = System.getenv().get("WEATHER_SERVICE_TEMPLATE_URL");
    private static final String DEFAULT_LOCATION = "Ryazan";
    private static final String TODAY = "today";

    private RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public DailyForecast getDailyForecast(String location) throws WeatherServiceException {
        if (location == null) {
            location = DEFAULT_LOCATION;
        }
        String url = MessageFormat.format(SERVICE_URL, location.toLowerCase(), TODAY);
        DailyForecast forecast;

        try {
            forecast = restTemplate.getForObject(url, DailyForecast.class);
        } catch (RestClientException e) {
            throw new WeatherServiceException("Unable to get data from the service", e);
        }

        return forecast;
    }
}
