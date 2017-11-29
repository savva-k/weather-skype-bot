package com.imsavva.weatherclient;

/**
 * @author Savva Kodeikin
 */
public class WeatherServiceException extends RuntimeException {

    public WeatherServiceException() {
    }

    public WeatherServiceException(String message) {
        super(message);
    }

    public WeatherServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeatherServiceException(Throwable cause) {
        super(cause);
    }
}
