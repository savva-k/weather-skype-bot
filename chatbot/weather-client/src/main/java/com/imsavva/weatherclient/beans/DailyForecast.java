package com.imsavva.weatherclient.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * @author Savva Kodeikin
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyForecast {

    Map<String, ForecastEntry> detail;

    public DailyForecast() {

    }

    public Map<String, ForecastEntry> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, ForecastEntry> detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Daily forecast from different services:\n");

        for (Map.Entry<String, ForecastEntry> e : getDetail().entrySet()) {
            builder.append(e.getKey());
            builder.append(": ");
            builder.append(e.getValue().toString());
        }

        return builder.toString();
    }
}
