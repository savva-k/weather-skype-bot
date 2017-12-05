package com.imsavva.weatherclient.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Savva Kodeikin
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeeklyForecast {

    Map<String, List<ForecastEntry>> detail;

    public WeeklyForecast() {
    }

    public Map<String, List<ForecastEntry>> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, List<ForecastEntry>> detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Daily forecast from different services:\n");

        for (Map.Entry<String, List<ForecastEntry>> e : getDetail().entrySet()) {
            builder.append(e.getKey());
            builder.append(": ");
            builder.append(e.getValue().stream().map(f -> f.toString()).collect(Collectors.joining(",")));
        }

        return builder.toString();
    }
}
