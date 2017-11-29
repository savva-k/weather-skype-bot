package com.imsavva.weatherclient.beans;

import java.util.Date;

/**
 * @author Savva Kodeikin
 */
public class ForecastEntry {

    private Date date;
    private int tempDay;
    private int tempNight;
    private int windDirection;
    private int windSpeed;
    private int precipitationProbability;
    private int pressureDay;
    private int pressureNight;

    public ForecastEntry() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTempDay() {
        return tempDay;
    }

    public void setTempDay(int tempDay) {
        this.tempDay = tempDay;
    }

    public int getTempNight() {
        return tempNight;
    }

    public void setTempNight(int tempNight) {
        this.tempNight = tempNight;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(int precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public int getPressureDay() {
        return pressureDay;
    }

    public void setPressureDay(int pressureDay) {
        this.pressureDay = pressureDay;
    }

    public int getPressureNight() {
        return pressureNight;
    }

    public void setPressureNight(int pressureNight) {
        this.pressureNight = pressureNight;
    }
}
